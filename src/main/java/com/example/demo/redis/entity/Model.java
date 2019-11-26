package com.example.demo.redis.entity;

import org.springframework.data.annotation.Id;

public class Model {
	@Id
	private String id;
	
	private String desc;

	public Model() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Model)	{
			return this.desc.equals(((Model)obj).desc) ; 
		}
		return false;
	}
}
