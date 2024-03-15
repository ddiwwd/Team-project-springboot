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

@Alias("ER_IngredientsDto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ER_IngredientsDto {
	private String ingredient;
	private int recipecode;
	private String ri_amount;
}
