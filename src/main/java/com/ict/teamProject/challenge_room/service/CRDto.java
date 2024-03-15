package com.ict.teamProject.challenge_room.service;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//24.02.18 생성
@Getter
@Setter
@Alias("CRoom")
@Builder
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor
public class CRDto {
	private int challNo; //방 번호
	private String goal; //목표
	private int challCapacity; // 정원
	private int implementation; //이행률
	private int gLimit; // 성별 제한
	private int ageMin; //나이 최소
	private int ageMax; //나이 최대
	private int pFee;  // 참여비
	private String cYN; //공개유무
	private java.util.Date cCreateDate; //방 생성일
	private java.util.Date cStartDate; //챌린지 시작일
	private java.util.Date cEndDate;  //챌린지 종료일
	private String challContent; //내용
	private String challTitle; //제목
	private String challArea; //지역
	private String manager; //방장
	private List participantsData;
	
}
