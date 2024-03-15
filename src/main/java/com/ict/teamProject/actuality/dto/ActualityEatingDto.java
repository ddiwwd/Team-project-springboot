package com.ict.teamProject.actuality.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("ActualityEatingDto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualityEatingDto {
	private String id;
	private Date ae_date;
	private String ae_diettype;
	private String ae_foodname;
	private String foodname;
	private int total_calory;
	private int total_carbohydrate;
	private int total_protein;
	private int total_fat;
	private int total_sodium;
	private int total_cholesterol;
}
