package com.example.demo.entity;

import java.util.UUID;

public class Message {
	private UUID id;
	private String company;
	private String state;
	private String channel;
	private String lob;
	private String policyNo;
	private String policySeqNo;
	private String queueName;
	private String key;
	private String request;
	private String response;

	public Message() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicySeqNo() {
		return policySeqNo;
	}

	public void setPolicySeqNo(String policySeqNo) {
		this.policySeqNo = policySeqNo;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", company=" + company + ", state=" + state + ", channel=" + channel + ", lob="
				+ lob + ", policyNo=" + policyNo + ", policySeqNo=" + policySeqNo + ", queueName=" + queueName
				+ ", key=" + key + ", request=" + request + ", response=" + response + "]";
	}

	
}
