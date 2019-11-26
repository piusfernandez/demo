package com.example.demo.entity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.entity.LombokEntity;
import com.example.demo.entity.dto.LombokEntityDto;

@Mapper
public interface LombokMapper {
	@Mapping(source = "id", target = "did")
	@Mapping(source = "name", target = "dname")
	@Mapping(source = "address", target = "daddress")
	public LombokEntityDto lombokEntityToLombokEntityDto(LombokEntity lombokEntity);
	
	
}
