package com.ict.teamProject.pay;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ict.teamProject.pay.dto.PaymentListDto;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import java.nio.file.StandardCopyOption;
@RestController

@CrossOrigin(origins = "http://localhost:3333")
public class PaymentListController {
	PaymentListService service;
	public PaymentListController(PaymentListService service) {
		this.service = service;
	}
	
	@GetMapping("/PaymentList") //조회
	public List<PaymentListDto> getAllList(@RequestParam String id){
		System.out.println("들어온 아이디:"+id);
		List<PaymentListDto> paylist = service.findPayList(id);
		System.out.println("아웃풋 : "+paylist);
		for (PaymentListDto payment : paylist) {
		    System.out.println(payment.getId());       // id 속성의 값 출력
		    System.out.println(payment.getPayDate());  // payDate 속성의 값 출력
		    System.out.println(payment.getPayType());  // payType 속성의 값 출력
		    System.out.println(payment.getPayName());  // payName 속성의 값 출력
		    System.out.println(payment.getPayPrice()); // payPrice 속성의 값 출력
		    System.out.println(payment.getPayMethod()); // payMethod 속성의 값 출력
		}
		return paylist;
	}
	
	@PostMapping("/Payment/Write.do")
	public int insertList(@RequestBody Map map) {
		System.out.println(map);
		int before_affected = service.before_insertPayment(map);
		System.out.println(before_affected);
		int affected = service.insertPayment(map);
		System.out.println(affected);
		return affected;
	}
}