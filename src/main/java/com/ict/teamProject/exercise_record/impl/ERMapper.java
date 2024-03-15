package com.ict.teamProject.exercise_record.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.exercise_record.ERDto;


@Mapper
public interface ERMapper {

	List getData(String id);


}
