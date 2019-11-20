package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import com.example.demo.entity.Vehicle;
import com.example.demo.entity.Year;

@Service
public class RestClientService {

	@Autowired
	private RestOperations restOperations;

	@Value("${rest.client.url}")
	private String url;

	@Value("${rest.client.yearmakemode.url}")
	private String urlYearMakeMode;

	public Year getVehYearData(int year) {

		return restOperations.getForObject(url, Year.class, year);
	}
	
	public List<Vehicle> getVehicleForYearMakeModel(int year, String make, String model)	{
		
		return restOperations.getForObject(urlYearMakeMode, List.class, year, make, model);
	}
	
	

	
	
}
