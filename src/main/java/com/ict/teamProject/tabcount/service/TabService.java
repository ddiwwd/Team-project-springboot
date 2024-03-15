package com.ict.teamProject.tabcount.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.ict.teamProject.files.service.FilesDto;

import jakarta.servlet.http.HttpServletRequest;


public interface TabService<T> {

	Integer findComm(LocalDate today);

	void setComm(Integer num);

	void insertComm();

	Integer findExer(LocalDate today);

	void setExer(Integer num);

	void insertExer();

	Integer findDiet(LocalDate today);

	void setDiet(Integer num);

	void insertDiet();

	Integer findDiary(LocalDate today);

	void setDiary(Integer num);

	void insertDiary();

	List<TabDto> findAllTab();






}
