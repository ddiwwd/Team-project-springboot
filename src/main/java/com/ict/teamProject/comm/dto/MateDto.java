package com.ict.teamProject.comm.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("MateDto")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MateDto {
	private String mate_id;
	private String name;
	private int fNum;
	private int mNum;
	private int sNum;
	private String profilePath;
	private int favorable_rating;
}
