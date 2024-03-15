package com.ict.teamProject.chat.service;
import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Alias("ChatDto")
@Builder
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor
public class ChatDto {
	private int mno;
	private String rno;
	private String id;
	private String ruser;
	private int tno;
	private String content;
	private char notice;
	private String sendDate;
}
