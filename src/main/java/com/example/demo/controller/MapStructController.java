package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.LombokEntity;
import com.example.demo.entity.dto.LombokEntityDto;
import com.example.demo.entity.mapper.LombokMapper;

@RestController
public class MapStructController {

	@Autowired
	LombokMapper lombokMapper;
	
	@PostMapping("/mapstruct/")
	public LombokEntityDto getMappedStructure(@RequestBody LombokEntity lombokEntity)	{
		
		return lombokMapper.lombokEntityToLombokEntityDto(lombokEntity);
	}
}
