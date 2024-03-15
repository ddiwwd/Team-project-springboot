package com.ict.teamProject.tabcount.web;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.teamProject.mate_room.service.MPDto;
import com.ict.teamProject.mate_room.service.MRDto;
import com.ict.teamProject.mate_room.service.MRService;
import com.ict.teamProject.tabcount.service.TabDto;
import com.ict.teamProject.tabcount.service.TabService;


//24.02.28 생성
@Controller
@RequestMapping("/tab")
@RestController
@CrossOrigin(origins = "http://localhost:3333")
public class TabController {
	
	//서비스 주입
	@Autowired
	private TabService<TabDto> service;
	
	//페이지 카운트 입력]
	@PostMapping("/count.do")
	@ResponseBody
	public void count(@RequestBody Map<String, String> map) {
	    String counter = map.get("counter");
	    System.out.println("counter: " + counter);
	    if("community".equals(counter)) {
	        LocalDate today = LocalDate.now();
	        if(service.findComm(today) != null) {
	            Integer num = service.findComm(today);
	            num = num+1;
	            service.setComm(num);
	        }
	        else {
	            service.insertComm();
	        }                    
	    }
	    if("exercise".equals(counter)) {
	        LocalDate today = LocalDate.now();
	        if(service.findExer(today) != null) {
	        	Integer num = service.findExer(today);
	            num = num+1;
	            service.setExer(num);
	        }
	        else {
	            service.insertExer();
	        }                    
	    }
	    if("diet".equals(counter)) {
	        LocalDate today = LocalDate.now();
	        if(service.findDiet(today) != null) {
	        	Integer num = service.findDiet(today);
	            num = num+1;
	            service.setDiet(num);
	        }
	        else {
	            service.insertDiet();
	        }                    
	    }
	    if("diary".equals(counter)) {
	        LocalDate today = LocalDate.now();
	        if(service.findDiary(today) != null) {
	        	Integer num = service.findDiary(today);
	            num = num+1;
	            service.setDiary(num);
	        }
	        else {
	            service.insertDiary();
	        }                    
	    }
	}
	
	//페이지 카운트 입력]
	@GetMapping("/getCount.do")
	@ResponseBody
	public List getCount() {
		List<TabDto> dto = new ArrayList();
		dto = service.findAllTab();
		return dto;
	}
}
