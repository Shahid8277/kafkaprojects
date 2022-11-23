package com.technoelevate.kafka.producer.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technoelevate.kafka.producer.dto.TopicDetailsDto;
import com.technoelevate.kafka.producer.entity.Topic;
import com.technoelevate.kafka.producer.response.TopicResponse;
import com.technoelevate.kafka.producer.service.TopicCreationService;

@RestController
@RequestMapping("api/v1/kafka")
public class KafkaTopicController {

	@Autowired
	private TopicCreationService creationService;

	@PostMapping("topic")
	public ResponseEntity<TopicResponse> createTopic(@RequestBody Topic topic) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(TopicResponse.builder().error(false).message(creationService.crateTopic(topic)).build());
	}
	
	@PutMapping("topic")
	public ResponseEntity<TopicResponse> updateTopic(@RequestBody Topic topic) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(TopicResponse.builder().error(false).message(creationService.updateTopic(topic)).build());
	}

	@GetMapping("topic")
	public List<TopicDetailsDto> getAllTopicWithDesc() {
		return creationService.getAllTopicWithDesc();
	}

	@GetMapping("topic/{topicName}")
	public TopicDetailsDto getTopicWithDesc(@PathVariable(name = "topicName") String topicName) {
		return creationService.getTopicWithDesc(topicName);
	}

}
