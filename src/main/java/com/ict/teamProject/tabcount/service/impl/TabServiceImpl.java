package com.ict.teamProject.tabcount.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.ict.teamProject.bbs.service.BBSDto;
import com.ict.teamProject.bbs.service.BBSService;
import com.ict.teamProject.bbs.service.LikesDto;
import com.ict.teamProject.files.service.FilesDto;
import com.ict.teamProject.tabcount.service.TabDto;
import com.ict.teamProject.tabcount.service.TabService;



@Service
public class TabServiceImpl implements TabService<TabDto> {

	//매퍼 인터페이스 주입
	@Autowired
	private TabMapper mapper;

	@Override
	public Integer findComm(LocalDate today) {
		return mapper.findComm(today);
	}

	@Override
	public void setComm(Integer num) {
		mapper.setComm(num);
		
	}

	@Override
	public void insertComm() {
		mapper.insertComm();
		
	}

	@Override
	public Integer findExer(LocalDate today) {
		return mapper.findExer(today);
	}

	@Override
	public void setExer(Integer num) {
		mapper.setExer(num);
	}

	@Override
	public void insertExer() {
		mapper.insertExer();
		
	}

	@Override
	public Integer findDiet(LocalDate today) {
		return mapper.findDiet(today);
	}

	@Override
	public void setDiet(Integer num) {
		mapper.setDiet(num);
		
	}

	@Override
	public void insertDiet() {
		mapper.insertDiet();
	}

	@Override
	public Integer findDiary(LocalDate today) {
		return mapper.findDiary(today);
	}

	@Override
	public void setDiary(Integer num) {
		mapper.setDiary(num);	
	}

	@Override
	public void insertDiary() {
		mapper.insertDiary();	
	}

	@Override
	public List<TabDto> findAllTab() {
		return mapper.findAllTab();
	}
	
	
}
