package com.ict.teamProject.bbs.service;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("LikesDto")
@Builder
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor
public class LikesDto {
	private int lno;
	private String id;
	private int bno;
	private int cno;
	private java.sql.Date lDate;
	
}

