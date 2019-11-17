package com.example.demo.service;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CamelService implements Processor{

	Logger log = LoggerFactory.getLogger(CamelService.class);
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Vehicle vehicle = (Vehicle)exchange.getIn().getBody();
		//Map dataMap = objectMapper.convertValue(vehicle, Map.class);
		String json = objectMapper.writeValueAsString(vehicle);
		//log.info(json);
		exchange.getIn().setBody(json);
	}

}
