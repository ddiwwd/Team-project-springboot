package com.ict.teamProject.commentline.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.commentline.service.CommentLineDto;

@Mapper
public interface CommentLineMapper {

	//전체 조회
	List findAll(Map map);
	
	//레코드 하나
	CommentLineDto findByCommentLine(Map map);
	
	//입력
	int save(Map map);
	int save_parent(Map map);
	
	//씨큐리티 용
	int saveUser(Map<String, String> map);
	

	//레코드 하나 수정
	int updatefindByCommentLine(String c_no, String ccomment);
	
	//레코드 하나 삭제
	int delete(int c_no);

	List findrecent_comment(Map map);
	List userprofiles(Map map);
}
