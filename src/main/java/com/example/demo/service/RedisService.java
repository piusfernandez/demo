package com.example.demo.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import net.prcins.services.model.Company;

@Service
public class RedisService {
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	Logger logger = LoggerFactory.getLogger(RedisService.class);
	
	public void loadData(Map<String, Company> companies) {
		HashOperations<String, String, Company> hashOperation = redisTemplate.opsForHash();	
		logger.info("" + companies);
		for (Map.Entry<String,Company> entry : companies.entrySet())	{  
            System.out.println("Key = " + entry.getKey() + 
                             ", Value = " + entry.getValue());
            hashOperation.put("CompanyList", (String)entry.getKey(), (Company)entry.getValue());
		}
	}
}
