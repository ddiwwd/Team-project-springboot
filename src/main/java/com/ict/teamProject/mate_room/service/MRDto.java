package com.ict.teamProject.mate_room.service;
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
@Alias("MRoom")
@Builder
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor
public class MRDto {
	private int mateNo; //방 번호
	private String mateSport; //운동
	private String mateArea; //지역
	private java.util.Date mateDate; //메이트 시작일
	private int mateCapacity; // 정원
	private String rYN; //방 공개유무
	private int matchingRoom; // 매칭 방
	private java.util.Date mCreateDate; //방 생성일
	private String mateTitle; //제목
	private String mateContent; //내용
	private int gLimit; // 성별 제한
	private int ageMin; //나이 최소
	private int ageMax; //나이 최대
	private String mYN; //매칭 유무
	private String manager; //방장
	private List participantsData; //참여자 데이타

	
}
