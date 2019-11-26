package com.example.demo.camel.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.redis.entity.Vehicle;
import com.example.demo.service.CamelService;
import com.example.demo.service.RedisService;

@Component
public class VehicleLoadRoute extends RouteBuilder{

	DataFormat dataFormat = new BindyCsvDataFormat(Vehicle.class);
	
	@Autowired
	CamelService camelService;
	
	@Autowired
	RedisService redisService;
		
	
/*	@Override
	public void configure() throws Exception {
		log.info("vehicle route started...");
		
		onException(Exception.class).log(LoggingLevel.ERROR, "Exception in route ${body}")
		.stop();
		
		from("{{fromvehicleroute}}")
		.unmarshal(dataFormat)
		.split(body())
		//.parallelProcessing()
		//.process(camelService)
		//.marshal().json(JsonLibrary.Jackson)
		//.convertBodyTo(String.class)
		.log("input to url is ${body}")
	    //.setHeader(Exchange.HTTP_METHOD, constant("POST"))
		//.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		//.to("{{rider.load.vehicle.url}}")
		.process(redisService)
		.log("${body}")
		.end()
		;
	}*/

	
	
	@Override
	public void configure() throws Exception {
		log.info("vehicle route started...");
		
		onException(Exception.class).log(LoggingLevel.ERROR, "Exception in route ${body}")
		.stop();
		
		from("{{fromvehicleroute}}")
		.unmarshal(dataFormat)
		.split(body())
		//.log("input to url is ${body}")
		.process(redisService)
		//.log("${body}")
		.end()
		;
	}
}
