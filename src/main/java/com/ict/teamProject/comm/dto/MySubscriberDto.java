package com.ict.teamProject.comm.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("MySubscriberDto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MySubscriberDto {
	private String id;
	private String name;
	private int fNum;
	private int mNum;
	private int sNum;
	private String profilePath;
}
