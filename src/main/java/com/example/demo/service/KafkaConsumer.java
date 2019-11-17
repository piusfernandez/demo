package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Vehicle;

@Service
public class KafkaConsumer {

	Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
	
	@KafkaListener(topics = "kafka_example", groupId="group_id")
	public void consume(String message)	{
		log.info(message);
	}
	
	@KafkaListener(topics = "kafka_example_json", groupId="group_json", containerFactory = "kafkaVehicleListenerContainerFactory")
	public void consumeJson(Vehicle vehicle)	{
		log.info(vehicle.toString());
	}
	
}
