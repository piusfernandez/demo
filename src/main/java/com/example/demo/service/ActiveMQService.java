package com.example.demo.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.util.Util;

@Service
public class ActiveMQService implements MQService {

	JmsTemplate jmsTemplate;

	public ActiveMQService(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/*
	 * 
	 * message data structure
	 * {
	 *  "company" : "",
	 *  "state" : "",
	 *  "channel" : "",
	 *  "lob" : "",
	 *  "policyNo" : "",
	 *  "policySeqNo : "",
	 * 	"queueName" : "violation",
	 *  "key" : "queueName:company:state:channel:lob:policyNo:policySeqNo",
	 *  "request" : {}
	 *  "response" : {}
	 * }
	 * */
	
	@Override
	public void sendMessage(String message) {		
		Util.updateKey(
		 Util.updateId(
		  Util.convertJsonToObject(message)));
	}

}
