package com.ict.teamProject.food;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.food.dto.FoodListDto;
import com.ict.teamProject.selftest.dto.AllergyInfoDto;
import com.ict.teamProject.selftest.dto.HateFoodInfoDto;
import com.ict.teamProject.selftest.dto.MemberAllergyDto;
import com.ict.teamProject.selftest.dto.MemberHateFoodDto;
import com.ict.teamProject.selftest.dto.InbodyInfoDto;

@Mapper
public interface FoodMapper {
	public List<FoodListDto> findrecipe(String id, String category);

	public List<FoodListDto> findAllrecipe(String id);

	public List<FoodListDto> getfoodinfo(String foodname);
	
}
