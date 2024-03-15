package com.ict.teamProject.bbs.service;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("BBSUsersProfileDto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BBSUsersProfileDto {
	private String id;
	//친구인지 여부를 불러오는 멤버변수
	private int isFriend;
	//구독 상태인지를 불러오는 멤버변수
	private int isSubTo;
	//유저의 프로필을 불러오는 멤버변수
	private String profilePath;
}
