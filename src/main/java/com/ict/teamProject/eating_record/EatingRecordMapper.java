package com.ict.teamProject.eating_record;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.eating_record.dto.EatingRecordDto;
import com.ict.teamProject.eating_record.dto.ER_IngredientsDto;

@Mapper
public interface EatingRecordMapper {

	int savediet(String id, String mealtype, String eating_foodname, int eating_recipeCode);
	List<EatingRecordDto> getdailydiet(String id);
	int updatediet(String id, String mealtype, String eating_foodname, int eating_recipeCode);
//	List<Map<String, Object>> getIngredients(int recipeCode);
	int checkdailydata(String id);
	int checkAlldata(String id);
	int defaultbdata(String id);
	int defaultldata(String id);
	int defaultddata(String id);
	List<ER_IngredientsDto> getrecipeNingredients(int eating_recipeCode);
	int checkdailyexercisedata(String id);
	void defaultexercise1(String id);
	void defaultexercise2(String id);
	void defaultexercise3(String id);
}
