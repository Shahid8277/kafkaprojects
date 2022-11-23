package com.technoelevate.kafka.producer.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_DEFAULT)
public class Topic {
	
	private String topicName;
	
	private int partitions;
	
	private int replicationFactor;
	
}
