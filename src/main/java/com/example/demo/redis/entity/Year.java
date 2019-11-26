package com.example.demo.redis.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

public class Year {
	
	@Id
	private String id;
	
	private int year;
	
	//@Field(type = Nested, includeInParent = true)
	private List<Make> makes;

	public Year() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<Make> getMakes() {
		if(makes == null)
			makes = new ArrayList<>();
		return makes;
	}

	public void setMakes(List<Make> makes) {
		this.makes = makes;
	}

}
