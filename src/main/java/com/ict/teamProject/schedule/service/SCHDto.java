package com.ict.teamProject.schedule.service;

import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("SCHDto")
@Builder
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor
public class SCHDto {
	private int sNo; // 글번호
	private String id; // 아이디
	private String sTitle; // 제목 
	private String sContent; // 내용
	private String sEat;  // 음식
	private String sExer; // 운동
	private String sDest; // 목적지
	private int cal; // 타입
	private String sArea; //시작 지역
	private LocalDateTime  start; //시작
	private LocalDateTime  end; //끝
	private char sCom; //완료여부
	private int rPathNo;  //경로
	private String sMate; //메이트
}
