package com.ict.teamProject.tabcount.service;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("Tab")
@Builder
@NoArgsConstructor  
@AllArgsConstructor
public class TabDto {
	private java.sql.Date tabDate;
	private int exercisePage; 
	private int dietPage;
	private int mindPage;
	private int communityPage;
}
