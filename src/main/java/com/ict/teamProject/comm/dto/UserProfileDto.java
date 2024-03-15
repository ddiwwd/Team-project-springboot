package com.ict.teamProject.comm.dto;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("UserProfileDto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
	private String id;
	private String name;
	private Date date;
	private String profilePath;
	private String proIntroduction;
	private int backfiletype;
	private List<FriendDto> friendsList;
	private List<String> requestedFriendsList; //친구 요청을 보낸 유저 목록
}
