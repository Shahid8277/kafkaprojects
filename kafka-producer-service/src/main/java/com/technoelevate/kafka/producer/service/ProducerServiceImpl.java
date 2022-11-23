package com.technoelevate.kafka.producer.service;

import java.sql.Timestamp;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProducerServiceImpl implements ProducerService {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	@Value("${spring.kafka.topic: kafka-topic}")
	private String topic;
	
	@Value("${spring.kafka.partition-id: 1}")
	private int partitionId;

	private String msgLog;

	public String sendMessageWithPartition(Object message) {
		int key = ThreadLocalRandom.current().nextInt(100000, 1000000);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		ListenableFuture<SendResult<String, Object>> listenableFuture = kafkaTemplate.send(topic, partitionId,
				timestamp.getTime(), "" + key, message);
		return addCallback(listenableFuture, message);
	}
	
	public String sendMessageWithTopic(Object message) {
		int key = ThreadLocalRandom.current().nextInt(100000, 1000000);
		ListenableFuture<SendResult<String, Object>> listenableFuture =kafkaTemplate.send(topic, ""+key, message);
		return addCallback(listenableFuture, message);
	}
	
	private String addCallback(ListenableFuture<SendResult<String, Object>> listenableFuture,Object message) {
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
			@Override
			public void onSuccess(SendResult<String, Object> result) {
				log.info("message sent, partition={}, offset={}", result.getRecordMetadata().partition(),
						result.getRecordMetadata().offset());
				msgLog = "message sent, partition={} :" + result.getRecordMetadata().partition() + " , offset={} :"
						+ result.getRecordMetadata().offset();
			}

			@Override
			public void onFailure(Throwable throwable) {
				log.warn("failed to send, message={}", message, throwable);
				msgLog = "failed to send, message={} :" + message + "  " + throwable.getMessage();
			}
		});
		return msgLog;
	}
}
