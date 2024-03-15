package com.ict.teamProject.food.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("FoodListDto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodListDto {
	private String foodname;
	private int dataType;
	private String category;
	private String recipe_title;
	private String recipe_url;
	private String recipe_img;
	private int RECIPECODE;
	private String recipe_seq;
	private String INGREDIENT;
	private String RI_AMOUNT;
	private int calory;
	private int carbohydrate;
	private int protein;
	private int fat;
	private int sodium;
	private int cholesterol;
}
