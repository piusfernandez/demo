package com.example.demo.redis.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Make {
	@Id
	private String id;

	private int code;
	private String name;

	List<Series> series;

	public Make() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int i) {
		this.code = i;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Series> getSeries() {
		if(series == null)
			series = new ArrayList<>();
		return series;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Make)	{
			return this.code == ((Make)obj).code && this.name.equals(((Make)obj).name) ; 
		}
		return false;
	}

	
}
