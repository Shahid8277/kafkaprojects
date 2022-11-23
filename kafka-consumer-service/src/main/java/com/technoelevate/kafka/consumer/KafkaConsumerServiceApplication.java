package com.technoelevate.kafka.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableKafka
public class KafkaConsumerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerServiceApplication.class, args);
	}
}
