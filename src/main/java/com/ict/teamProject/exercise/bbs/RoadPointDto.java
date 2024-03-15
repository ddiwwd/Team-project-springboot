package com.ict.teamProject.exercise.bbs;

import java.sql.Time;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("RoadPointDto")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoadPointDto {
	private int rpath_no;
	private Object lat;
	private Object lng;
	private int order_num;
	private String pointname;
}
