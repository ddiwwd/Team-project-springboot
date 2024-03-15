package com.ict.teamProject.member_history.service;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;


public interface MemberHistoryService<T> {

	//전체보기용
	List<MemberHistoryDto> selectAll(Map map);

}
