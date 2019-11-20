package com.example.demo.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
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
	public RestOperations restOperations(final ClientHttpRequestFactory clientHttpRequestFactory)	{
		return new RestTemplate(clientHttpRequestFactory);
	}

	
	@Bean
	public PoolingHttpClientConnectionManager connectionManager()	{
		/*
		 * apache http pool
		 * max total connection of 100
		 * specific route 20
		 * */
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(100);
		connectionManager.setDefaultMaxPerRoute(20);
		HttpHost host = new HttpHost("http://localhost", 8080);
		connectionManager.setMaxPerRoute(new HttpRoute(host), 20);
		return connectionManager;
	}
	
	/*
	 * To add connect/ read timeouts to RestTemplate
	 * */
	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory(
			@Value("${rest.api.connect.timeout}") final int connectTimeout,
			@Value("${rest.api.read.timeout}") final int readTimeout
			)	{
		
		/*
		 * connection timeout - fails when server down 
		 * socket timeout - fails if request take more than 3s 
		 * */
		RequestConfig requestConfig = RequestConfig
				.custom()
				.setConnectionRequestTimeout(300000)
				.setSocketTimeout(300000)
				.build();
		
		CloseableHttpClient httpClient = HttpClients
				.custom()
				.setConnectionManager(connectionManager())
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setDefaultRequestConfig(requestConfig)
				.build();
		
		//HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		httpComponentsClientHttpRequestFactory.setConnectTimeout(connectTimeout);
		httpComponentsClientHttpRequestFactory.setReadTimeout(readTimeout);
		return httpComponentsClientHttpRequestFactory;
	}
	
	
}
