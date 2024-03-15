package com.ict.teamProject.chat.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.bbs.service.BBSDto;
import com.ict.teamProject.bbs.service.BBSUsersProfileDto;
import com.ict.teamProject.bbs.service.LikesDto;
import com.ict.teamProject.chat.service.ChatDto;
import com.ict.teamProject.files.service.FilesDto;

@Mapper
public interface ChatMapper {

	
	//레코드 하나
	ChatDto findChatdata(Map map);
	
	//입력
	int save(Map map);

	ChatDto findChatingPerson(String id);

	List<ChatDto> allChat(String id);

	List<ChatDto> allMateChat(Map map);

	int mateIn(Map map);

	List<ChatDto> allChallChat(Map map);

	int challIn(Map map);


	
	
	
}
