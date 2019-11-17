package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

	@Bean
	public RestOperations testTemplate(final ClientHttpRequestFactory clientHttpRequestFactory)	{
		return new RestTemplate(clientHttpRequestFactory);
	}
	
	/*
	 * To add connect/ read timeouts to RestTemplate
	 * */
	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory(
			@Value("${rest.api.connect.timeout}") final int connectTimeout,
			@Value("${rest.api.read.timeout}") final int readTimeout
			)	{
		HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpComponentsClientHttpRequestFactory.setConnectTimeout(connectTimeout);
		httpComponentsClientHttpRequestFactory.setReadTimeout(readTimeout);
		return httpComponentsClientHttpRequestFactory;
	}
	
	
}
