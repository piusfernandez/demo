package com.example.demo.service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.redis.entity.Vehicle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RedisService implements Processor {
	
/*	@Autowired
	StringRedisTemplate redisTemplate;
*/
	Logger logger = LoggerFactory.getLogger(RedisService.class);
	
/*	public void loadData(Map<String, Company> companies) {
		HashOperations<String, String, Company> hashOperation = redisTemplate.opsForHash();	
		logger.info("" + companies);
		for (Map.Entry<String,Company> entry : companies.entrySet())	{  
            System.out.println("Key = " + entry.getKey() + 
                             ", Value = " + entry.getValue());
            hashOperation.put("CompanyList", (String)entry.getKey(), (Company)entry.getValue());
		}
	}
	
	public void loadYearData(Year year) {
		HashOperations<String, Integer, Year> hashOperation = redisTemplate.opsForHash();	
        hashOperation.put("hldi.vehicle.data", year.getYear(), year);
	}*/

	/*
	 * create the following hash in redis
	 * hldi:cache:year:makecode
	 * hldi:cache:year:makename
	 * hldi:cache:year:makename:model
	 * hldi:cache:year:makename:series
	 * hldi:cache:year:makename:series:model
	 * hldi:cache:year:makename:model:series
	 * hldi:cache:year:makename:series:model:vindata
	 * hldi:cache:year:makename:model:vindata
	 * 
	 * */
	
	private final static String YEAR_MAKECODE 						= "Xhldi:cache:year:makecode";
	private final static String YEAR_MAKENAME 						= "Xhldi:cache:year:makename";
	private final static String YEAR_MAKENAME_MODEL 				= "Xhldi:cache:year:makename:model";
	private final static String YEAR_MAKENAME_SERIES 				= "Xhldi:cache:year:makename:series";
	private final static String YEAR_MAKENAME_SERIES_MODEL 			= "Xhldi:cache:year:makename:series:model";
	private final static String YEAR_MAKENAME_MODEL_SERIES 			= "Xhldi:cache:year:makename:model:series";
	private final static String YEAR_MAKENAME_SERIES_MODEL_VINDATA 	= "Xhldi:cache:year:makename:series:model:vindata";
	private final static String YEAR_MAKENAME_MODEL_VINDATA 		= "Xhldi:cache:year:makename:model:vindata";
	
	

	StringRedisTemplate redisTemplate;
	HashOperations<String, String, String> hashOperation;
	
	public RedisService(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
		hashOperation = redisTemplate.opsForHash();
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Vehicle vehicle = (Vehicle)exchange.getIn().getBody();
		//vehicle.setId(UUID.randomUUID().toString());
		//logger.info(vehicle.toString());
		
		
		//HashOperations<String, String, String> hashOperation = redisTemplate.opsForHash();
		String yearMakeCode = hashOperation.get(YEAR_MAKECODE, String.valueOf(vehicle.getModelYear()));
		pushInRedis(vehicle, hashOperation, String.valueOf(vehicle.getModelYear()), yearMakeCode, YEAR_MAKECODE);

		String yearMakeName = hashOperation.get(YEAR_MAKENAME, String.valueOf(vehicle.getModelYear()));
		pushInRedis(vehicle, hashOperation, String.valueOf(vehicle.getModelYear()), yearMakeName, YEAR_MAKENAME);

		String yearMakeNameModel = hashOperation.get(YEAR_MAKENAME_MODEL, String.valueOf(vehicle.getModelYear()) + ":" + vehicle.getMakeName());
		pushInRedis(vehicle, hashOperation, String.valueOf(vehicle.getModelYear()) + ":" + vehicle.getMakeName(), yearMakeNameModel, YEAR_MAKENAME_MODEL);

		String yearMakeNameSeries = hashOperation.get(YEAR_MAKENAME_SERIES, String.valueOf(vehicle.getModelYear()) + ":" + vehicle.getMakeName());
		pushInRedis(vehicle, hashOperation, String.valueOf(vehicle.getModelYear()) + ":" + vehicle.getMakeName(), yearMakeNameSeries, YEAR_MAKENAME_SERIES);

		String yearMakeNameSeriesModel = hashOperation.get(YEAR_MAKENAME_SERIES_MODEL, String.valueOf(vehicle.getModelYear()) + ":" + vehicle.getMakeName() + ":" + vehicle.getSeriesName());
		pushInRedis(vehicle, hashOperation, String.valueOf(vehicle.getModelYear()) + ":" + vehicle.getMakeName() + ":" + vehicle.getSeriesName(), yearMakeNameSeriesModel, YEAR_MAKENAME_SERIES_MODEL);

		String yearMakeNameModelSeries = hashOperation.get(YEAR_MAKENAME_MODEL_SERIES, String.valueOf(vehicle.getModelYear()) + ":" + vehicle.getMakeName() + ":" + vehicle.getModelDesc());
		pushInRedis(vehicle, hashOperation, String.valueOf(vehicle.getModelYear()) + ":" + vehicle.getMakeName() + ":" + vehicle.getModelDesc(), yearMakeNameModelSeries, YEAR_MAKENAME_MODEL_SERIES);

		String yearMakeNameSeriesModelVindata = hashOperation.get(YEAR_MAKENAME_SERIES_MODEL_VINDATA, String.valueOf(vehicle.getModelYear()) + ":" + vehicle.getMakeName() + ":" + vehicle.getModelDesc() + ":" + vehicle.getSeriesName());
		pushInRedis(vehicle, hashOperation, String.valueOf(vehicle.getModelYear()) + ":" + vehicle.getMakeName() + ":" + vehicle.getSeriesName() + ":" + vehicle.getModelDesc(), yearMakeNameSeriesModelVindata, YEAR_MAKENAME_SERIES_MODEL_VINDATA);

		String yearMakeNameModelVindata = hashOperation.get(YEAR_MAKENAME_MODEL_VINDATA, String.valueOf(vehicle.getModelYear()) + ":" + vehicle.getMakeName() + ":" + vehicle.getModelDesc());
		pushInRedis(vehicle, hashOperation, String.valueOf(vehicle.getModelYear()) + ":" + vehicle.getMakeName() + ":" + vehicle.getModelDesc(), yearMakeNameModelVindata, YEAR_MAKENAME_MODEL_VINDATA);

	}

	/*
	 * 
	 * Input
	 * Vehicle - Vin data
	 * HashOperations - to store hash with key / value ( type = string ) 
	 * key - hash -> key
	 * value - hash -> value
	 * hashName - hash name
	 * 
	 * Based of the hash, key, value store in appropriate hash
	 * 
	 * */
	private void pushInRedis(Vehicle vehicle, HashOperations<String, String, String> hashOperation, 
			String key, String value, String hashName)
			throws JsonProcessingException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		if(value!=null){
			Set<String> make =  mapper.readValue(value, Set.class);
			if(YEAR_MAKECODE.equals(hashName)){
				make.add(String.valueOf(vehicle.getMakeCode()));
			} else if(YEAR_MAKENAME.equals(hashName)){
				make.add(String.valueOf(vehicle.getMakeName()));
			} else if(YEAR_MAKENAME_MODEL.equals(hashName))	{
				make.add(vehicle.getModelDesc());
			} else if(YEAR_MAKENAME_SERIES.equals(hashName))	{
				make.add(vehicle.getSeriesName());
			} else if(YEAR_MAKENAME_SERIES_MODEL.equals(hashName))	{
				make.add(vehicle.getModelDesc());
			} else if(YEAR_MAKENAME_MODEL_SERIES.equals(hashName))	{
				make.add(vehicle.getSeriesName());
			} else if(YEAR_MAKENAME_SERIES_MODEL_VINDATA.equals(hashName))	{
				make.add(mapper.writeValueAsString(vehicle));
			} else if(YEAR_MAKENAME_MODEL_VINDATA.equals(hashName))	{
				make.add(mapper.writeValueAsString(vehicle));
			}
			hashOperation.put(hashName, key, mapper.writeValueAsString(make));
		} else {
			/*first make added to a year*/
			Set<String> first = new HashSet<>();
			if(YEAR_MAKECODE.equals(hashName)){
				first.add(String.valueOf(vehicle.getMakeCode()));
			} else if(YEAR_MAKENAME.equals(hashName)){
				first.add(String.valueOf(vehicle.getMakeName()));
			} else if(YEAR_MAKENAME_MODEL.equals(hashName))	{
				first.add(vehicle.getModelDesc());
			} else if(YEAR_MAKENAME_SERIES.equals(hashName))	{
				first.add(vehicle.getSeriesName());
			} else if(YEAR_MAKENAME_SERIES_MODEL.equals(hashName))	{
				first.add(vehicle.getModelDesc());
			} else if(YEAR_MAKENAME_MODEL_SERIES.equals(hashName))	{
				first.add(vehicle.getSeriesName());
			} else if(YEAR_MAKENAME_SERIES_MODEL_VINDATA.equals(hashName))	{
				first.add(mapper.writeValueAsString(vehicle));
			} else if(YEAR_MAKENAME_MODEL_VINDATA.equals(hashName))	{
				first.add(mapper.writeValueAsString(vehicle));
			}
			
			hashOperation.put(hashName, key, mapper.writeValueAsString(first));
		}
	}
}
