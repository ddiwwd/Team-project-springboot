package com.ict.teamProject.selftest.dto;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("AllergyInfoDto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllergyInfoDto {
	private int allergy_no;
	private String allergy_name;
	private String allergy_imagepath;
}
