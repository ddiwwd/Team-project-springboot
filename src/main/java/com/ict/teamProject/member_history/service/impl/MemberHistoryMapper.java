package com.ict.teamProject.member_history.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.member_history.service.MemberHistoryDto;

@Mapper
public interface MemberHistoryMapper {

	//전체 조회
	List findAll(Map map);
	
	//레코드 하나
	MemberHistoryDto findByMemberHistory(Map map);
	
	//씨큐리티 용
	int saveUser(Map<String, String> map);
}
