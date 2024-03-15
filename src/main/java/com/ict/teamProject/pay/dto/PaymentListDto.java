package com.ict.teamProject.pay.dto;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Alias("PaymentListDto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentListDto {
	private String id;
	private Timestamp payDate;
	private int payType;
	private String payName;
	private int payPrice;
	private String payMethod;
}
