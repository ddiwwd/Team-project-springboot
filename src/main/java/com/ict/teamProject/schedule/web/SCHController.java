package com.ict.teamProject.schedule.web;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ict.teamProject.schedule.service.SCHDto;
import com.ict.teamProject.schedule.service.SCHService;



@Controller
@RequestMapping("/sch")
@RestController
@CrossOrigin(origins = "http://localhost:3333")
public class SCHController {
	
	//서비스 주입
	@Autowired
	private SCHService<SCHDto> service;
	
	//스케쥴 등록
	@PostMapping("/insert.do")
	public void insert(@RequestBody Map<String, Object> map) {
		System.out.println("가져온 값은??"+map);
	    System.out.println("title: " + map.get("title"));
	    System.out.println("calendar: " + map.get("calendar"));
	    System.out.println("start: " + map.get("start"));
	    System.out.println("end: " + map.get("end"));
	    System.out.println("startArea: " + map.get("startArea"));
	    System.out.println("endArea: " + map.get("endArea"));
	    System.out.println("content: " + map.get("content"));
	    System.out.println("eat: " + map.get("eat"));
	    System.out.println("exercise: " + map.get("exercise"));
	    System.out.println("id: " + map.get("id"));
	    
	    SCHDto schedule = new SCHDto();

	    String startDateTime = (String) map.get("start");
	    String endDateTime = (String) map.get("end");
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	    LocalDateTime start = LocalDateTime.parse(startDateTime, formatter);
	    LocalDateTime end = LocalDateTime.parse(endDateTime, formatter);
	    schedule.setSTitle((String) map.get("title"));
	    schedule.setCal((Integer) map.get("calendar"));
	    schedule.setStart(start);
	    schedule.setEnd(end);
	    schedule.setSArea((String) map.get("startArea"));
	    schedule.setSDest((String) map.get("endArea"));
	    schedule.setSContent((String) map.get("content"));
	    schedule.setId((String) map.get("id"));

	    if (map.get("eat") != null) {
	        schedule.setSEat((String) map.get("eat"));
	    }

	    if (map.get("exercise") != null) {
	        schedule.setSExer((String) map.get("exercise"));
	    }
	    service.insert(schedule);
	}
	
	//스케쥴 수정
	@PostMapping("/update.do")
	public void update(@RequestBody Map<String, Object> map) {
		service.update(map);
	}
	
	//스케쥴 삭제
	@PostMapping("/delete.do")
	public void delete(@RequestBody Map<String, Object> map) {
		service.delete(map);
	}
	
	//스케쥴 하나 조회
	@PostMapping("/seleteOne.do")
	public SCHDto seleteOne(@RequestBody Map<String, Object> map) {
		SCHDto schedule = new SCHDto();
		schedule = service.seleteOne(map.get("id").toString());
		
		return schedule;
	}
	
	//스케쥴 전체 조회
	@PostMapping("/seleteAll.do")
	public List seleteAll(@RequestBody Map<String, Object> map) {
		List<SCHDto> record = new ArrayList();
		record = service.seleteAll(map.get("id").toString());
		
		return record;
	}
	
	//오늘 스케쥴 전체 조회
	@PostMapping("/seleteTodayAll.do")
	public List seleteTodayAll(@RequestBody Map<String, Object> map) {
		List<SCHDto> record = new ArrayList();
		record = service.seleteTodayAll(map.get("id").toString());
		
		return record;
	}
	
	//장소 조회
	@GetMapping("/priorAddress")
	public List priorAddress(@RequestParam String id) {
		List<SCHDto> record = service.priorAddress(id);
		return record;
	}
	
	@GetMapping("/updateRestaurant")
	public int updateRestaurant(@RequestParam String id, int cal, int sNo, String sDest) {
		System.out.println("들어온 값 :" + sNo + "|"+sDest);
		int affected = service.updateRestaurant(sNo, sDest);
		
		return affected;
	}
	
	//해당 날짜 스케쥴 전체 조회
	@PostMapping("/seleteAnyAll.do")
	public List seleteAnyAll(@RequestBody Map<String, Object> map) {
		List<SCHDto> record = new ArrayList();
		record = service.seleteAnyAll(map);
		return record;
	}
	
}
