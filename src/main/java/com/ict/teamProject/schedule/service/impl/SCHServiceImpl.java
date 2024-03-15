package com.ict.teamProject.schedule.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.ict.teamProject.schedule.service.SCHDto;
import com.ict.teamProject.schedule.service.SCHService;



@Service
public class SCHServiceImpl implements SCHService<SCHDto> {

	//매퍼 인터페이스 주입
	@Autowired
	private SCHMapper mapper;

	@Override
	public void insert(SCHDto schedule) {
		mapper.insert(schedule);	
	}

	@Override
	public SCHDto seleteOne(String string) {
		return mapper.seleteOne(string);
	}

	@Override
	public List<SCHDto> seleteAll(String string) {
		return mapper.seleteAll(string);
	}

	@Override
	public List<SCHDto> seleteTodayAll(String string) {
		return mapper.seleteTodayAll(string);
	}

	@Override
	public void delete(Map<String, Object> map) {
		mapper.delete(map);
		
	}

	@Override
	public List priorAddress(String id) {
		return mapper.priorAddress(id);
	}

	@Override
	public int updateRestaurant(int sNo, String sDest) {
		return mapper.updateRestaurant(sNo, sDest);
	}

	@Override
	public List<SCHDto> seleteAnyAll(Map<String, Object> map) {
		return mapper.seleteAnyAll(map);
	}

	@Override
	public void update(Map<String, Object> map) {
		mapper.update(map);
		
	}

}
