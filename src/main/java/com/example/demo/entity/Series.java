package com.example.demo.entity;

import org.springframework.data.annotation.Id;

public class Series {
	@Id
	private String id;

	private String name;

	public Series() {
	}

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Series)	{
			return this.name.equals(((Series)obj).name) ; 
		}
		return false;
	}
}
