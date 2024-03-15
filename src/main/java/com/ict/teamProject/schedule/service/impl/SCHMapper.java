package com.ict.teamProject.schedule.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.schedule.service.SCHDto;


@Mapper
public interface SCHMapper {

	void insert(SCHDto schedule);

	SCHDto seleteOne(String string);

	List<SCHDto> seleteAll(String string);

	List<SCHDto> seleteTodayAll(String string);

	void delete(Map<String, Object> map);

	List priorAddress(String id);

	int updateRestaurant(int sNo, String sDest);

	List<SCHDto> seleteAnyAll(Map<String, Object> map);

	void update(Map<String, Object> map);
}
