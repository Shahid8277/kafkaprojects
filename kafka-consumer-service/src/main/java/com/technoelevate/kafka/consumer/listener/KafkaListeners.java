package com.technoelevate.kafka.consumer.listener;

import static com.technoelevate.kafka.consumer.constant.ConsumerConstants.GROUP_ID;
import static com.technoelevate.kafka.consumer.constant.ConsumerConstants.TOPIC0;
import static com.technoelevate.kafka.consumer.constant.ConsumerConstants.TOPIC1;
import static com.technoelevate.kafka.consumer.constant.ConsumerConstants.TOPIC_PARTITION;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaListeners {

	@KafkaListener(topics = TOPIC0, groupId = GROUP_ID)
	void listener(String message) {
		log.info("Listener [{}]", message);
	}

	@KafkaListener(topics = { TOPIC0, TOPIC1 }, groupId = GROUP_ID)
	void commonListenerForMultipleTopics(String message) {
		log.info("MultipleTopicListener - [{}]", message);
	}

	@KafkaListener(topicPartitions = @TopicPartition(topic = TOPIC0, partitionOffsets = {
			@PartitionOffset(partition = TOPIC_PARTITION, initialOffset = "0") }), groupId = GROUP_ID)
	void listenToParitionWithOffset(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
			@Header(KafkaHeaders.OFFSET) int offset) {
		log.info("ListenToPartitionWithOffset [{}] from partition-{} with offset-{}", message, partition, offset);
	}

	@KafkaListener(topics = TOPIC0)
	void listenerForRoutingTemplate(String message) {
		log.info("RoutingTemplate BytesListener [{}]", message);
	}

	@KafkaListener(topics = TOPIC0)
	@SendTo(TOPIC1)
	String listenAndReply(String message) {
		log.info("ListenAndReply [{}]", message);
		return "This is a reply sent to 'topic-reply' topic after receiving message at 'topic' topic";
	}

}
