package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Vehicle;

@RestController
public class KafkaController {
	
	@Autowired
	KafkaTemplate<String, Object> kafkaTemplate;

	private static final String TOPIC = "kafka_example";
	
	@PutMapping("/publish/{message}")
	public String publishMessage(@RequestParam("message") final String message)	{
		kafkaTemplate.send(TOPIC, message);
		return "published message successfully to kafka!";
	}
	
	@PostMapping("/publish/vehicle")
	public String publishVehicle(@RequestBody final Vehicle vehicle)	{
		kafkaTemplate.send(TOPIC, vehicle);
		return "published vehicle successfully to kafka!";
	}
	
}
