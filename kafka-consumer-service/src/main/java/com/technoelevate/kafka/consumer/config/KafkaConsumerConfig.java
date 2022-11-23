//package com.technoelevate.kafka.consumer.config;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.annotation.PartitionOffset;
//import org.springframework.kafka.annotation.TopicPartition;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.kafka.support.converter.StringJsonMessageConverter;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Configuration
//public class KafkaConsumerConfig {
//
//	@Value("${spring.kafka.consumer.bootstrap-servers: localhost:9092, localhost:9092, localhost:9092}")
//	private String bootstrapServers;
//
//	@Value("${spring.kafka.consumer.group-id: group-id}")
//	private String groupId;
//	
//	private long reqTimeOut=300000;
//	private long heardBeatInterval=1000;
//	private long pullInterval=900000;
//	private long pullRecord=100;
//	private long sessionTimeOut=600000;
//	/*request.timeout.ms=300000
//heartbeat.interval.ms=1000
//max.poll.interval.ms=900000
//max.poll.records=100
//session.timeout.ms=600000
//	*/
//
//	@Bean
//	public ConsumerFactory<String, Object> consumerFactory() {
//		Map<String, Object> config = new HashMap<>();
//
//		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//		config.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, heardBeatInterval);
//		config.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, reqTimeOut);
//		config.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, pullInterval);
//		config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, pullRecord);
//		config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeOut);
//		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
//		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//
//		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
//				new JsonDeserializer<>(Object.class));
//	}
//
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerJsonFactory() {
//		ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//		factory.setConsumerFactory(consumerFactory());
//		factory.setMessageConverter(new StringJsonMessageConverter());
//		factory.setBatchListener(true);
//		return factory;
//	}
//
//	
//	
////	@KafkaListener(topicPartitions = @TopicPartition(topic = "topic"), groupId = "group-id")
////	void listenMessageWithTopic(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
////			@Header(KafkaHeaders.OFFSET) int offset) {
////		log.info("ListenToPartitionWithOffset [{}] from partition-{} with offset-{}", message, partition, offset);
////	}
//	
////	@KafkaListen	er(topics = { "topic"}, groupId = "group-id")
////	void commonListenerForMultipleTopics(@Payload String message) {
////		log.info("MultipleTopicListener - [{}]", message);
////	}
//	
//	
//
//}
