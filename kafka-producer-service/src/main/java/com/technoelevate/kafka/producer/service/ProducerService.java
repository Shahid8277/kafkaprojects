package com.technoelevate.kafka.producer.service;

public interface ProducerService {
	
	public String sendMessageWithPartition(Object message);
	
	public String sendMessageWithTopic(Object message);
	
}
