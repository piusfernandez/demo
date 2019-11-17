package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Make;
import com.example.demo.entity.Model;
import com.example.demo.entity.Series;
import com.example.demo.entity.Vehicle;
import com.example.demo.entity.Year;
import com.example.demo.repo.VehicleRepository;
import com.example.demo.repo.YearRepository;

@Service
public class VehicleService {

	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	YearRepository yearRepository;
	
	Logger log = LoggerFactory.getLogger(VehicleService.class);
	
	/*
	 * save vehicle data
	 * check for duplicate and do not save
	 * */
	public Vehicle saveVehicle(Vehicle vehicle) {
		try{
			Vehicle checkVehicle = vehicleRepository.findByVinPrefixAndModelYearAndMakeNameAndModelDesc(
					vehicle.getVinPrefix(),vehicle.getModelYear(), vehicle.getMakeName(), vehicle.getModelDesc());
			if(checkVehicle != null && checkVehicle.getVinPrefix() != null)	{
				log.info("Duplicate: Vehicle already saved.. " + vehicle.getVinPrefix());
				vehicle.setId(checkVehicle.getId());
				return vehicle;
			}
		}catch(org.elasticsearch.index.IndexNotFoundException e){
			log.info("elastic index not created.. " + e.getMessage());
		}
		vehicle.setId(UUID.randomUUID().toString());
		vehicleRepository.save(vehicle);
		return vehicle;
	}

	public Vehicle deleteVehicle(Vehicle vehicle) {
		Vehicle checkVehicle = vehicleRepository.findByVinPrefixAndModelYearAndMakeNameAndModelDesc(
				vehicle.getVinPrefix(), vehicle.getModelYear(), vehicle.getMakeName(), vehicle.getModelDesc());
		if(checkVehicle != null)	{
			vehicleRepository.deleteById(checkVehicle.getId());
			vehicle.setId(checkVehicle.getId());
		}
		return vehicle;
	}

	/*
	 * save vehicle data in year -> list<make> -> make[list<model>], make[list<series>]
	 * if there are same multiple year, make, model for different vin prefix do not add it to year structure
	 * */
	synchronized
	public Year saveYear(Vehicle vehicle) {
		Year year = null;
		try{
			year = yearRepository.findByYear(vehicle.getModelYear());				
		}catch(org.elasticsearch.index.IndexNotFoundException e){
			log.info("elastic index not created.. " + e.getMessage());
		}

		Year newYear = loadYearMakeModelSeries(vehicle, year);			
		if(newYear != null)
			yearRepository.save(newYear);
		return yearRepository.findByYear(vehicle.getModelYear());
	}

	public Year deleteYearMakeModel(Vehicle vehicle) {
		
		Year year = null;
		try{
			year = yearRepository.findByYear(vehicle.getModelYear());
		}catch(org.elasticsearch.index.IndexNotFoundException e){
			log.info("elastic index not created.. " + e.getMessage());
		}

		yearRepository.save(deleteYearMakeModelSeries(vehicle, year));
		return yearRepository.findByYear(vehicle.getModelYear());

	}
	
	public List<Vehicle> getVehicleByYearMakeModel(final int year, final String make, final String model) {
		log.info("vehicleByYearMakeModel - no cache.. pull data from elasticsearch");
		return vehicleRepository.findByModelYearAndMakeNameAndModelDesc(year, make, model);
	}
	
	public Year getVehicleByYear(final int year) {
		log.info("vehicleByYear - no cache.. pull data from elasticsearch");
		return yearRepository.findByYear(year);
	}
	
	private Year loadYearMakeModelSeries(Vehicle vehicle, Year year) {
		if(year == null)	{
			/*
			 * first time a new year entry is stored
			 * */
			year = new Year();
			year.setYear(vehicle.getModelYear());
			
			Make make = new Make();
			make.setId(UUID.randomUUID().toString());
			make.setCode(vehicle.getMakeCode());
			make.setName(vehicle.getMakeName());
			
			Model model = new Model();
			model.setId(UUID.randomUUID().toString());
			model.setDesc(vehicle.getModelDesc());
			
			Series series = new Series();
			series.setId(UUID.randomUUID().toString());
			series.setName(vehicle.getSeriesName());

			make.getModels().add(model);
			make.getSeries().add(series);		
			year.getMakes().add(make);
		}
		else	{
			Make newMake = new Make();
			newMake.setId(UUID.randomUUID().toString());
			newMake.setCode(vehicle.getMakeCode());
			newMake.setName(vehicle.getMakeName());
			
			Model newModel = new Model();
			newModel.setId(UUID.randomUUID().toString());
			newModel.setDesc(vehicle.getModelDesc());
			
			Series newSeries = new Series();
			newSeries.setId(UUID.randomUUID().toString());
			newSeries.setName(vehicle.getSeriesName());
			
			boolean isMakeExist = (year.getMakes().contains(newMake))?true:false;
			if(isMakeExist)	{
				boolean isModelExist = getMake(year, vehicle.getMakeName()).getModels().contains(newModel)?true:false;
				if(!isModelExist){
					getMake(year, vehicle.getMakeName()).getModels().add(newModel);					
				} else {
					/*
					 * its a duplicate
					 * */
					//log.info("Duplicate: Year/ Make/ Model already saved.. " + vehicle.getModelYear() + ":" + vehicle.getMakeName() + ":" + vehicle.getModelDesc());
					return null;
				}
				boolean isSeriesExist = getMake(year, vehicle.getMakeName()).getSeries().contains(newSeries)?true:false;				
				if(!isSeriesExist){
					getMake(year, vehicle.getMakeName()).getSeries().add(newSeries);
				}
			} else {
				year.getMakes().add(newMake);
				getMake(year, vehicle.getMakeName()).getModels().add(newModel);
				getMake(year, vehicle.getMakeName()).getSeries().add(newSeries);
			}
		}
		
		return year;
	}


	private Year deleteYearMakeModelSeries(Vehicle vehicle, Year year) {
		Make newMake = new Make();
		newMake.setCode(vehicle.getMakeCode());
		newMake.setName(vehicle.getMakeName());
		
		Model newModel = new Model();
		newModel.setDesc(vehicle.getModelDesc());
		
		Series newSeries = new Series();
		newSeries.setName(vehicle.getSeriesName());
		
		boolean isMakeExist = (year.getMakes().contains(newMake))?true:false;
		if(isMakeExist)	{
			boolean isModelExist = getMake(year, vehicle.getMakeName()).getModels().contains(newModel)?true:false;
			if(isModelExist){
				getMake(year, vehicle.getMakeName()).getModels().remove(newModel);					
			}
			boolean isSeriesExist = getMake(year, vehicle.getMakeName()).getSeries().contains(newSeries)?true:false;				
			if(isSeriesExist){
				getMake(year, vehicle.getMakeName()).getSeries().remove(newSeries);
			}
		} 

		return year;
	}

	
	private Make getMake(Year year, String make){
		for(Make vmake : year.getMakes()){
			if(make.equalsIgnoreCase(vmake.getName()))	{
				return vmake;
			}
		}
		return null;
	}

	public Long getVehicleCount() {
		return vehicleRepository.count();
	}

	public Long getTotalYearCount()	{
		return yearRepository.count();
	}

	public int getCountForAYear(int year)	{
		Year pullYearData = yearRepository.findByYear(year);
		return pullYearData.getMakes().size();
	}
	

}
