package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.util.Util;

@Service
public class PublishDataService {

	@Autowired
	ActiveMQService mqService;
	
	public void sendMessage(String message) {
		
		String queueName = Util.findQueueName(message);
		
		mqService.sendMessage(
		 Util.convertObjectToJson( 		
		  Util.updateKey(
		   Util.updateId(
		    Util.convertJsonToObject(message)
		   )
		  )
		 )
		 , queueName);
	}
}
