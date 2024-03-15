package com.ict.teamProject.pay;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ict.teamProject.pay.dto.PaymentListDto;

@Service
public class PaymentListService {
	
	//mapper 생성자 주입
	private PaymentListMapper mapper;
	public PaymentListService(PaymentListMapper mapper) {
		this.mapper = mapper;
	}
	
	//유저 PayList 목록
	public List<PaymentListDto> findPayList(String id) {
		return mapper.findPayList(id);
	}
	
	//부트페이 결제 진행 후 Data 넣기
	public int before_insertPayment(Map map) {
		return mapper.before_insertPayment(map);
	}
	public int insertPayment(Map map) {
		return mapper.insertPayment(map);
	}
}
