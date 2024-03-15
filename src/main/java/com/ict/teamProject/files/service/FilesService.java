package com.ict.teamProject.files.service;

import java.util.Map;

public interface FilesService<T> {
	
	//해당 게시글의 파일 가져오기
	T selectOne(Map map);
	
	//입력/수정/삭제용
	int insert(Map map);
	int update(T record);
	int delete(T record);
}
