package com.ict.teamProject.chat.service;

import java.util.List;
import java.util.Map;


public interface ChatService<T> {
	
	//상세보기용
	//T selectOne(int bno);
	
	//메세지 입력/수정/삭제용
	int insert(Map map);
	/*
	int insertFile(Map map);
	int update(ChatDto dto);
	
	//삭제용
	int deleteBBS(int bno);
	int deleteFiles(int bno);
	*/

	ChatDto selectChat(Map map);

	ChatDto whoChating(String id);

	List<ChatDto> allChating(String id);

	List<ChatDto> allMateChat(Map map);

	int mateIn(Map map);

	List<ChatDto> allChallChat(Map map);

	int challIn(Map map);

}
