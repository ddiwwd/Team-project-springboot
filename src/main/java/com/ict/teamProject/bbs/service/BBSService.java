package com.ict.teamProject.bbs.service;

import java.util.List;
import java.util.Map;

import com.ict.teamProject.files.service.FilesDto;

import jakarta.servlet.http.HttpServletRequest;


public interface BBSService<T> {

	//전체보기용
	List<BBSDto> selectAll(Map map);
	List selectFiles(int bno);
	
	//자기 게시글 보기
	List<BBSDto> selectMy(String id);
	
	//상세보기용
	T selectOne(int bno);
	
	//입력/수정/삭제용
	Map insert(Map map);
	int insertFile(Map map);
	int update(BBSDto dto);
	
	//삭제용
	int deleteBBS(int bno);
	int deleteFiles(int bno);

	
	int findIsFriend(Map<String, String> ids);
	int findIsSubto(Map<String, String> ids);
	String findProfilePathById(String id);
	
	//좋아요
	int findLikes(int bno);
	void setLikes(LikesDto likes);
	void deleteLikes(LikesDto likes);
	List whereLikes(int bno);
	String findUserProfileByLike(String like);
	List<String> findUserByLike(int bno);
	List<BBSDto> findAllList(Map map);
}
