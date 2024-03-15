package com.ict.teamProject.bbs.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.bbs.service.BBSDto;
import com.ict.teamProject.bbs.service.LikesDto;

@Mapper
public interface BBSMapper {

	//전체 조회
	List<BBSDto> findAll(Map map);
	List findFile(int bno);
	
	//레코드 하나
	BBSDto findByBBS(int bno);
	
	//자기 게시글 보기
	List<BBSDto> findMyByBBS(String id);
	
	//입력
	int save(Map map);
	int saveFiles(Map map);
	
	//씨큐리티 용
	int saveUser(Map<String, String> map);
	

	//레코드 하나 수정
	int update(BBSDto dto);
	
	//레코드 하나 삭제
	int deleteFiles(int bno);
	int deleteBBS(int bno);
	
	// 시퀀스 값을 먼저 가져오기
	int getSeqNextVal();
	
	//받은 아이디가 로그인한 유저 아이디와 친구 관계인지 판단
	int findIsFriend(Map<String, String> ids);
	
	//받은 아이디가 로그인한 유저 아이디와 구독 관계인지 판단
	int findIsSubto(Map<String, String> ids);
	
	//유저의 프로필 가져오기
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
