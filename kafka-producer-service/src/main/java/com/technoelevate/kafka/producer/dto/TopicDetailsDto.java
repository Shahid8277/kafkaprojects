package com.technoelevate.kafka.producer.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicDetailsDto {

	private String topicName;
	private String topicId;
	private int partitionCount;
	private int replicationFactor;
	List<TopicDescDto> topicDesc;
	
}
