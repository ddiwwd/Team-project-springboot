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
@Alias("IM")
@Builder
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor
public class ImplDto {
	private String id; //아이디
	private String exercise; //운동 이행률
	private String eatting; //식단 이행률
	private Date recordDate; //입력날짜

}
