package com.ict.teamProject.selftest.dto;

import java.sql.Date;
import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("InbodyInfoDto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InbodyInfoDto {
	private String id;
	private int inb_weight;
	private int inb_smm;
	private int inb_bfm;
	private int inb_bmi;
	private int inb_pbf;
	private Date inb_date;
}
