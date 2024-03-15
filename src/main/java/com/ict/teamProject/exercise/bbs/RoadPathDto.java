package com.ict.teamProject.exercise.bbs;

import java.sql.Time;

import org.apache.ibatis.type.Alias;

import com.ict.teamProject.comm.dto.FriendDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("RoadPathDto")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoadPathDto {
	//road_path 테이블 등록용
	private Integer rpath_no;
	private String id; //경로를 등록한 유저
	private Object rpath_time; //소요 시간(분 단위)
	private String mainaddr; //메인 주소값
}
