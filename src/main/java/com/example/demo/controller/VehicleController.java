package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Make;
import com.example.demo.entity.Model;
import com.example.demo.entity.Series;
import com.example.demo.entity.Vehicle;
import com.example.demo.entity.Year;
import com.example.demo.service.VehicleService;

@RestController
public class VehicleController {

	
	
/*	
 
	ObjectMapper objectMapper = new ObjectMapper();

 	@Autowired
	RestHighLevelClient restHighLevelClient;
	
	@PostMapping("/vehicle")
	public Vehicle addVehicle(@RequestBody Vehicle vehicle)	{
		vehicle.setId(UUID.randomUUID().toString());
		  Map dataMap = objectMapper.convertValue(vehicle, Map.class);
		  //IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, book.getId()).source(dataMap);
		  IndexRequest indexRequest = new IndexRequest("rider", "vehicles", vehicle.getId()).source(dataMap);
		  try {
		    IndexResponse response = restHighLevelClient.index(indexRequest);
		  } catch(ElasticsearchException e) {
		    e.getDetailedMessage();
		  } catch (java.io.IOException ex){
		    ex.getLocalizedMessage();
		  }
		  return vehicle;
		
	}*/

	Logger log = LoggerFactory.getLogger(VehicleController.class);

	@Autowired
	VehicleService vehicleService;
	
	@PostMapping("/vehicle")
	@Caching(evict = { 
			  @CacheEvict(value = "vehicle", key = "#vehicle.id")})
	public Vehicle addVehicle(@RequestBody Vehicle vehicle)	{
		
		return vehicleService.saveVehicle(vehicle);
	}


	@PostMapping("/year")
	@Caching(evict = { 
			@CacheEvict(value = "year", key = "#vehicle.modelYear")})
	public @ResponseBody Year addYear(@RequestBody Vehicle vehicle)	{
		
		return vehicleService.saveYear(vehicle);
	}

	
	@DeleteMapping("/vehicle")
	@Caching(evict = { 
			  @CacheEvict(value = "vehicle", key = "#vehicle.id")})
	public Vehicle deleteVehicle(@RequestBody Vehicle vehicle)	{
		
		vehicleService.deleteVehicle(vehicle);
		return vehicle;
	}

	@DeleteMapping("/year")
	@Caching(evict = { 
			  @CacheEvict(value = "year", key = "#vehicle.modelYear")})
	public Vehicle deleteYearMakeModel(@RequestBody Vehicle vehicle)	{
		
		vehicleService.deleteYearMakeModel(vehicle);
		return vehicle;
	}

	@GetMapping("/vehicle/{year}/{make}/{model}")
	@Cacheable(value = "vehicle")
	/*
	 * a simple key composed of year + make + model is used as key 
	 * */
	public List<Vehicle> getVehicleByYearMakeSeries(
			@PathVariable("year") final int year,
			@PathVariable("make") final String make, 
			@PathVariable("model") final String model)	{
		return vehicleService.getVehicleByYearMakeModel(year, make, model);
	}

	@GetMapping("/vehicle/{year}")
	@Cacheable(value = "year")
	/*
	 * year is used as Cacheable key 
	 * */
	public Year getVehicleByYear(@PathVariable("year") final int year)	{
		return vehicleService.getVehicleByYear(year);
	}

	@GetMapping("/vehicle/make/{year}")
	/*
	 * year is used as Cacheable key 
	 * */
	public List<Make> getVehicleMakesByYear(
			@PathVariable("year") final int year
			)	{
		Year vyear = vehicleService.getVehicleByYear(year);
		return vyear.getMakes();
	}

	@GetMapping("/vehicle/model/{year}/{make}")
	/*
	 * year is used as Cacheable key 
	 * */
	public List<Model> getVehicleModelByMakesAndYear(
			@PathVariable("year") final int year,
			@PathVariable("make") final String make
			)	{
		Year vyear = vehicleService.getVehicleByYear(year);
		for(Make vmake : vyear.getMakes()){
			if(make.equals(vmake.getName()))	{
				return vmake.getModels();
			}
		}
		return null;
	}

	@GetMapping("/vehicle/series/{year}/{make}")
	/*
	 * year is used as Cacheable key 
	 * */
	public List<Series> getVehicleSeriesByMakesAndYear(
			@PathVariable("year") final int year,
			@PathVariable("make") final String make
			)	{
		Year vyear = vehicleService.getVehicleByYear(year);
		for(Make vmake : vyear.getMakes()){
			if(make.equals(vmake.getName()))	{
				return vmake.getSeries();
			}
		}
		return null;
	}

	
	@GetMapping("/vehicle/count")
	public Long getVehicleCount(){
		return vehicleService.getVehicleCount();
	}

	@GetMapping("/year/count")
	public Long getVehicleYearCount(){
		return vehicleService.getTotalYearCount();
	}

	@GetMapping("/year/count/{year}")
	public Integer getVehicleYearCount(@RequestParam("year") final int year){
		return vehicleService.getCountForAYear(year);
	}

/*	@GetMapping("/year/count/{year}/{make}")
	public Integer getVehicleYearMakeCount(
			@RequestParam("year") final int year,
			@RequestParam("make") final String make
			){
		return vehicleService.getCountForAYearAndMake(year, make);
	}*/


	// ----------------------------------------------------------- //
	
/*	@PostMapping("/vehicle")
	@Caching(evict = { 
			  @CacheEvict(value = "vehicle", key = "#vehicle.id")
			  , @CacheEvict(value = "year", key = "#vehicle.modelYear") })
	public Year oldAddVehicle(@RequestBody Vehicle vehicle)	{
		
		
		 * handle upsert
		 * 
		
		vehicle.setId(UUID.randomUUID().toString());
		Year year = null;
		try{
			year = yearRepository.findByYear(vehicle.getModelYear());
		}catch(org.elasticsearch.index.IndexNotFoundException e){
			
		}
		vehicleRepository.save(vehicle);
		yearRepository.save(loadYearMakeModelSeries(vehicle, year));
		return year;
	}*/

/*	@DeleteMapping("/vehicle")
	@Caching(evict = { 
			  @CacheEvict(value = "vehicle", key = "#vehicle.vinPrefix")
			  , @CacheEvict(value = "year", key = "#vehicle.modelYear") })
	public Vehicle deleleVehicle(@RequestBody Vehicle vehicle)	{
		
		
		 * handle upsert for vehicle ( key = vin prefix + year + make + model 
		 * year is append only ( key = year + make + model ) 
		 * NOTE: for a year + make + model you can have multiple vins which will be a separate api call
		 * 
		Vehicle existingVeh = null;
		
		try{
			existingVeh = vehicleRepository.findByVinPrefixAndModelYearAndMakeNameAndModelDesc(
				vehicle.getVinPrefix(), vehicle.getModelYear(), vehicle.getMakeName(), vehicle.getModelDesc());
		} catch(org.elasticsearch.index.IndexNotFoundException e){
			log.info(e.getMessage());
		}
		if(existingVeh != null && existingVeh.getVinPrefix() != null && existingVeh.getVinPrefix().length()>0)	{
			vehicleRepository.deleteById(existingVeh.getId());
			yearRepository.deleteByYearAndMakesModelsIn(vehicle.getModelYear(), vehicle.getMakeName(), vehicle.getModelDesc());
		}  
		return vehicle;
	}
*/	
	
	
/*	@GetMapping("/vehicle/{year}")
	public List<Vehicle> getVehicleByYear(@PathVariable("year") final int year)	{
		return vehicleRepository.findByModelYear(year);
	}*/
	
}
