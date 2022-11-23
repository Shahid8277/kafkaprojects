package com.technoelevate.kafka.producer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {
	private String empId;
	private String name;
	private long contact;
	private String email;
}
