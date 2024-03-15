package com.ict.teamProject.exercise.bbs;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("RoadSchDto")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoadSchDto {
	//ID, SCH_START, SCH_END, RPATH_NO
	private String id;
	private String sch_start;
	private String sch_end;
	private String rpath_no;
	private String start_pos;
	private String end_pos;
	private String mate; //같이 돌 메이트 아이디
}
