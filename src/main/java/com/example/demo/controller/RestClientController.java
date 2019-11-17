package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Make;
import com.example.demo.entity.Model;
import com.example.demo.entity.Vehicle;
import com.example.demo.entity.Year;
import com.example.demo.service.RestClientService;

@RestController
public class RestClientController {

	@Autowired
	RestClientService restClientService; 
	
	Logger log = LoggerFactory.getLogger(RestClientController.class);
	
	@GetMapping("/client/load/vehicle/{year}")
	public Year getVehYearDataViaRestClientCall(@RequestParam("year") final int year){
		Year yearData = restClientService.getVehYearData(year);
		
		/*
		 * for a year get all makes ex: 1981
		 * */
		List<Make> makes = yearData.getMakes();
		
		/*
		 * for a make get all models ex: BWM
		 * */
		final String make = "BMW"; 
		List<List<Model>> models = makes.stream()
			.filter(m -> m.getName().equals("BMW"))
			.map(m -> m.getModels())
			.collect(Collectors.toList())
			;

		/*
		 * consumer selects a specific model
		 * */
		Optional model = models.stream()
			.flatMap(List::stream)
			.filter(m -> m.getDesc().equals("R100"))
			.findFirst();
		
		if(model.isPresent())	{
			log.info("Year " + year + "Make " + make + " Model-" + ((Model)model.get()).getDesc());
		}
		return yearData;
	}
	
	@GetMapping("/client/get/vehicle/{year}/{make}/{model}")
	public Vehicle getVehicleForYearMakeModel(
			@RequestParam("year") final int year,
			@RequestParam("make") final String make,
			@RequestParam("model") final String model
			)	{
		return restClientService.getVehicleForYearMakeModel(year, make, model);
	}
}
