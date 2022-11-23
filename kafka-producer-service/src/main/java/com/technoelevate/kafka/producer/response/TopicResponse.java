package com.technoelevate.kafka.producer.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicResponse {
	
	private boolean error;
	
	private String message;
	
	@JsonInclude(value = Include.NON_DEFAULT)
	private Object data;
	
}
