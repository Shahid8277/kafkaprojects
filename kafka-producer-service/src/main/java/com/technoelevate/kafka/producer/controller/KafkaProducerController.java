package com.technoelevate.kafka.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technoelevate.kafka.producer.response.ProduceResponse;
import com.technoelevate.kafka.producer.service.ProducerService;

@RestController
@RequestMapping("api/v1/kafka/producer")
public class KafkaProducerController {

	@Autowired
	private ProducerService creationService;

	@PostMapping("topic-partition")
	public ResponseEntity<ProduceResponse> sendMessageWithPartition(@RequestBody Object message) {
		return ResponseEntity.status(HttpStatus.OK).body(ProduceResponse.builder().error(false)
				.message(creationService.sendMessageWithPartition(message)).build());
	}

	@PostMapping("topic")
	public ResponseEntity<ProduceResponse> sendMessageWithTopic(@RequestBody Object message) {
		return ResponseEntity.status(HttpStatus.OK).body(
				ProduceResponse.builder().error(false).message(creationService.sendMessageWithTopic(message)).build());
	}

}
