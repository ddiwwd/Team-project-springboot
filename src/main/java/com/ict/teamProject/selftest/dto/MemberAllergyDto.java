package com.ict.teamProject.selftest.dto;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("MemberAllergyDto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberAllergyDto {
	private String id;
	private String allergy_no;
}
