package com.example.demo.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

import com.example.demo.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

	public static String readLineByLineJava8(String filePath) 
	{
	    StringBuilder contentBuilder = new StringBuilder();
	    try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) 
	    {
	        stream.forEach(s -> contentBuilder.append(s).append("\n"));
	    }
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	    return contentBuilder.toString();
	}
	
	public static Message convertJsonToObject(String json){
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.convertValue(json, Message.class);
	}

	public static String convertObjectToJson(Message message){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Message updateId(Message message) {
		message.setId(UUID.randomUUID());
		return message;
	}

	public static Message updateKey(Message message) {
		String key = message.getKey();
		key.replaceFirst("queueName", message.getQueueName());
		key.replaceFirst("company", message.getCompany());
		key.replaceFirst("state", message.getState());
		key.replaceFirst("channel", message.getChannel());
		key.replaceFirst("lob", message.getLob());
		key.replaceFirst("policyNo", message.getPolicyNo());
		key.replaceFirst("policySeqNo", message.getPolicySeqNo());
		message.setKey(key);
		return message;
	}

	public static String findQueueName(String message) {
		return convertJsonToObject(message).getQueueName();
	}
}
