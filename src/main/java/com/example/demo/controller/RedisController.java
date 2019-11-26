package com.example.demo.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.redis.entity.Year;
import com.example.demo.service.RedisService;

import net.prcins.services.model.Company;

@RestController
public class RedisController {

	RedisService redisService;

	RedisController(RedisService redisService) {
		this.redisService = redisService;
	}

/*	@PostMapping("/companyinsert")
	public void companyInsert(@RequestBody Map<String, Company> companies) {
		redisService.loadData(companies);
	}

	@PostMapping("/vehicleinsert")
	public void vehicleInsert(Year year) {
		redisService.loadYearData(year);
	}*/
	
}
