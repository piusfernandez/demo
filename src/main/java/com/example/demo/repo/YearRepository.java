package com.example.demo.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.demo.entity.Year;

public interface YearRepository extends ElasticsearchRepository<Year, String> {
	Year findByYear(int year);
	void deleteByYearAndMakesModelsIn(int year, String make, String model);
}
