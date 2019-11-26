package com.example.demo.entity;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.mapper.LombokMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class LombokEntity {

	private String id;
	private String name;
	private String address;
	
	public static void main(String[] args) {
		LombokEntity lentity = LombokEntity.builder()
			.id("1")
			.name("abc")
			.address("123 main st")
			.build();
		
		log.info(lentity.toString());
	}
}
