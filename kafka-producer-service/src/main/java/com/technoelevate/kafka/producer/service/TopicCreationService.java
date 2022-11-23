package com.technoelevate.kafka.producer.service;

import java.util.List;

import com.technoelevate.kafka.producer.dto.TopicDetailsDto;
import com.technoelevate.kafka.producer.entity.Topic;

public interface TopicCreationService {
	
	public String crateTopic(Topic topic);
	
	public String updateTopic(Topic topic);

	public List<TopicDetailsDto> getAllTopicWithDesc();

	public TopicDetailsDto getTopicWithDesc(String topicName);
}
