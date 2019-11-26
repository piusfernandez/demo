package com.example.demo.service;

public interface MQService {
	public void sendMessage(final String message, final String queueName);
}
