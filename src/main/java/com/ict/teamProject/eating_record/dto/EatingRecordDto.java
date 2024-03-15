package com.ict.teamProject.eating_record.dto;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.ict.teamProject.eating_record.dto.ER_IngredientsDto;

@Alias("EatingRecordDto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EatingRecordDto {
	private String id;
	private String mealType;
	private String eating_foodname;
	private int eating_recipeCode;
	private Date eating_date;
	private int calory;
	private String recipe_title;
	private String recipe_url;
	private String recipe_seq;
	private String recipe_img;
	private List<ER_IngredientsDto> ingredients;
}
