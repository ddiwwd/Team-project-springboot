package com.ict.teamProject.member_history.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.ict.teamProject.member_history.service.MemberHistoryDto;
import com.ict.teamProject.member_history.service.MemberHistoryService;



@Service
public class MemberHistroyServiceImpl implements MemberHistoryService<MemberHistoryDto> {

	//매퍼 인터페이스 주입
	@Autowired
	private MemberHistoryMapper mapper;

	@Override
	public List<MemberHistoryDto> selectAll(Map map) {
		List records=mapper.findAll(map);
		return records;
	}
}
