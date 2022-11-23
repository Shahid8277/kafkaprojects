package com.technoelevate.kafka.producer.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicDescDto {
	
	private int partition;
	private int leader;
	private List<Integer> replicas;
	private List<Integer> isr;

}
