package com.example.demo.config;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Configuration
//@EnableScheduling
public class Redis {
	
	@Value("${cache.redis.host}")
	private String host;
	
	@Value("${cache.redis.port}")
	private int port;

	@Value("${cache.redis.sentinel}")
	private String sentinel;

	@Value("${cache.redis.sentinelauth}")
	private String password;

	@Value("${cache.useSentinel}")
	private boolean useSentinel;

	@Value("${cache.redis.master}")
	private String master;

/*	@Value("${cache.redis.auth}")
	private String auth;

	@Value("${cache.redis.queuename}")
	private String queueName;
*/
	private static final Logger logger = LoggerFactory.getLogger(Redis.class);

	
/*	@Bean
	JedisConnectionFactory jedisConnectionFactory()	{
		JedisConnectionFactory factory = new JedisConnectionFactory();
		if(useSentinel)
		{
			Set<String> sentinels = new LinkedHashSet<String>();
			String sentinelarr[] = sentinel.split(",");
			for(String sentinel : sentinelarr)
				sentinels.add(sentinel.trim());
			factory =  new JedisConnectionFactory(new RedisSentinelConfiguration(master, sentinels));
			factory.setPassword(password);
		}
		else
		{
			 factory.setUseSsl(useSsl);
			 factory.setUsePool(true);
			 factory.setHostName(host);
			 factory.setPort(port);
			 factory.setPassword(auth);
			 JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			 jedisPoolConfig.setMaxTotal(20);
			 jedisPoolConfig.setMinIdle(5);
			 jedisPoolConfig.setMaxIdle(10);
			 factory.setPoolConfig(jedisPoolConfig);
		}
		return factory;
	}
*/
	
	@Bean
	@Primary
	LettuceConnectionFactory lettuceConnectionFactorySentinal()	{
		LettuceConnectionFactory factory =  null;
		if(useSentinel){
			Set<String> sentinels = new LinkedHashSet<String>();
			String sentinelarr[] = sentinel.split(",");
			for(String sentinel : sentinelarr)
				sentinels.add(sentinel.trim());
			factory =  new LettuceConnectionFactory(new RedisSentinelConfiguration(master, sentinels));
			factory.setPassword(password);
		}
		return factory;
	}
	
	@Bean
	LettuceConnectionFactory lettuceConnectionFactory()	{
		LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(host, port);
		return connectionFactory;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplateForStringValue() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory());
		redisTemplate.setKeySerializer(getStringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashKeySerializer(getStringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		return redisTemplate;
	}

	@Bean
	public StringRedisSerializer getStringRedisSerializer()	{
		StringRedisSerializer serializer = new StringRedisSerializer();
		return serializer;
	}
	
	//Configure poolsize 
/*	@Bean
	public ExecutorService executorService()
	{
		return  Executors.newFixedThreadPool(10);
	}
*/
	@SuppressWarnings("deprecation")
	private ObjectMapper customObjectMapper() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.setVisibilityChecker(
	            mapper.getVisibilityChecker()
	                    .withFieldVisibility(Visibility.ANY)
	                    .withGetterVisibility(Visibility.NONE)
	                    .withSetterVisibility(Visibility.NONE)
	                    .withCreatorVisibility(Visibility.NONE));
	    mapper.enableDefaultTypingAsProperty(ObjectMapper.DefaultTyping.NON_FINAL, "@class"); // enable default typing
	    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	    return mapper;
	}

	
/*	@Bean
	MessageListenerAdapter messageListener() { 
	    return new MessageListenerAdapter(new UmModelRedisSubscriber());
	}
	
	@Bean
	ChannelTopic topic() {
	    return new ChannelTopic("RateTransientXmlEvents");
	}
	
	@Bean
	RedisMessageListenerContainer redisContainer() {
	    RedisMessageListenerContainer container 
	      = new RedisMessageListenerContainer(); 
	    container.setConnectionFactory(jedisConnectionFactory()); 
	    container.addMessageListener(messageListener(), topic()); 
	    return container; 
	}
*/	
	
	/*
	 * get message from redis um-queue
	 * message is then sent to output channel "receiver-channel"
	 * */
/*	@Bean
	public RedisQueueMessageDrivenEndpoint consumerEndPoint() {
		logger.info("|QueueName|" + queueName);
		RedisQueueMessageDrivenEndpoint endPoint = new RedisQueueMessageDrivenEndpoint(queueName,
				jedisConnectionFactory());
		endPoint.setOutputChannelName("receiverChannel");
		return endPoint;
	}*/
	
}