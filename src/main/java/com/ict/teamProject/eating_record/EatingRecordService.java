package com.ict.teamProject.eating_record;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ict.teamProject.eating_record.dto.EatingRecordDto;
import com.ict.teamProject.eating_record.dto.ER_IngredientsDto;
@Service
public class EatingRecordService {
	
	//mapper 생성자 주입
	private EatingRecordMapper mapper;
	public EatingRecordService(EatingRecordMapper mapper) {
		this.mapper = mapper;
	}
	public int savediet(String id, String mealtype, String eating_foodname, int eating_recipeCode) {
		return mapper.savediet(id, mealtype, eating_foodname, eating_recipeCode);
	}
	public List<EatingRecordDto> getdailydiet(String id){
		return mapper.getdailydiet(id);
	}
	public int updatediet(String id, String mealtype, String eating_foodname, int eating_recipeCode) {
		return mapper.updatediet(id, mealtype, eating_foodname, eating_recipeCode);
	}
	public int checkdailydata(String id) {
		return mapper.checkdailydata(id);
	}
	public int checkAlldata(String id) {
		return mapper.checkAlldata(id);
	}
	public int defaultbdata(String id) {
		return mapper.defaultbdata(id);
	}
	public int defaultldata(String id) {
		return mapper.defaultldata(id);		
	}
	public int defaultddata(String id) {
		return mapper.defaultddata(id);
	}
	public List<ER_IngredientsDto> getrecipeNingredients(int eating_recipeCode) {
		return mapper.getrecipeNingredients(eating_recipeCode);
	}
	public int checkdailyexercisedata(String id) {
		return mapper.checkdailyexercisedata(id);
	}
	public void defaultexercise(String id) {
		mapper.defaultexercise1(id);
		mapper.defaultexercise2(id);
		mapper.defaultexercise3(id);
	}
	
//	public List<Map<String, Object>> getIngredients(int recipeCode) {
//	    return mapper.getIngredients(recipeCode);
//	}
}
