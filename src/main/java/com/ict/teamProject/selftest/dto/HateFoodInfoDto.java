package com.ict.teamProject.selftest.dto;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("HateFoodInfoDto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HateFoodInfoDto {
	private int hatefood_no;
	private String hatefood_name;
	private String hatefood_imagepath;
}
