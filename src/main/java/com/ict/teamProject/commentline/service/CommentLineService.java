package com.ict.teamProject.commentline.service;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;


public interface CommentLineService<T> {

	//전체보기용
	List<CommentLineDto> selectAll(Map map);
	
	//상세보기용
	T selectOne(Map map);
	
	//입력/수정/삭제용
	//int insert(Map map);
	int insert(Map map, int type);
	int update(String c_no, String ccomment);
	int delete(int c_no);

	List<CommentLineDto> findrecent_comment(Map map);
	List userprofiles(Map map);
}
