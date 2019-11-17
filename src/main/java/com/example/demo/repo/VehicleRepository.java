package com.example.demo.repo;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Vehicle;

@Repository
public interface VehicleRepository extends ElasticsearchRepository<Vehicle, String> {

	List<Vehicle> findByModelYear(int year);
	List<Vehicle> findByModelYearAndMakeNameAndModelDesc(int year, String make, String model);
	Vehicle findByVinPrefixAndModelYearAndMakeNameAndModelDesc(String prefix, int year, String make, String model);
}
