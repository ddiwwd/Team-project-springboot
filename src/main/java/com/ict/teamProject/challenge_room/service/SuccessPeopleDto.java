package com.ict.teamProject.challenge_room.service;
import java.sql.Date;

import org.apache.ibatis.type.Alias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//24.02.22 생성
@Getter
@Setter
@Alias("SuccessPeopleDto")
@Builder
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor
public class SuccessPeopleDto {
	private int challNo;
	private String id; //아이디
	private int total_fee; //
	private int success_count; //성공 건수
	private int total_count; //유저 이행건수
	private String result; //성공 유무

}
