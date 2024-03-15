package com.ict.teamProject.pay;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.pay.dto.PaymentListDto;

@Mapper
public interface PaymentListMapper {
	public List<PaymentListDto> findPayList(String id);
	
	public int insertPayment(Map map);

	public int before_insertPayment(Map map);
}
