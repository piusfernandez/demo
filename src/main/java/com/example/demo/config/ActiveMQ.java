package com.example.demo.config;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@Description(value = "ActiveMQ Configuration")
public class ActiveMQ {

	@Value("${activemq.brokerurl}")
	private String brokerUrl;

	public static final String QUEUE_PREFILL = "queue.prefill";

	Logger log = LoggerFactory.getLogger(ActiveMQ.class);
	
	@Bean
	public Queue queue() {
		return new ActiveMQQueue(QUEUE_PREFILL);
	}

	@Bean
	  public ActiveMQConnectionFactory connectionFactory() {
	    ActiveMQConnectionFactory activeMQConnectionFactory =
	        new ActiveMQConnectionFactory();
	    activeMQConnectionFactory.setBrokerURL(brokerUrl);

	    return activeMQConnectionFactory;
	  }
	
	public PooledConnectionFactory connectionFactory(ActiveMQConnectionFactory connectionFactory) {
		PooledConnectionFactory pool = new PooledConnectionFactory(connectionFactory);
		pool.setMaxConnections(5);
		return pool;
	}

	@Bean(name = "jmsTemplate")
	public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory) {
		JmsTemplate template = new JmsTemplate(connectionFactory(connectionFactory));
		//JmsTemplate template = new JmsTemplate(connectionFactory);
		return template;
	}
	
	@Bean
    public JmsListenerContainerFactory<?> listenerFactory(ConnectionFactory connectionFactory,
    		DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        
        factory.setErrorHandler(t -> log.info("Error occured when listening to message " + t.getMessage()));
        
        return factory;
    }
}