package com.ict.teamProject.actuality;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.ict.teamProject.actuality.dto.ActualityEatingDto;
import com.ict.teamProject.schedule.service.SCHDto;
@RestController

@CrossOrigin(origins = "http://localhost:3333")
public class ActualityController {
	ActualityService service;
	public ActualityController(ActualityService service) {
		this.service = service;
	}
	
	@GetMapping("/saverecord/dietinfo.do") //조회
	public List<ActualityEatingDto> saveActualityEating(String id, String ae_foodname, String ae_diettype){
		System.out.println("들어온 유저 아이디 :" + id + "받은 음식:" + ae_foodname+ "식사 : " + ae_diettype);
		int checkdiet = service.checkdailydiet(id, ae_diettype);
		if(checkdiet == 0) {
			int saveYN = service.saveActuality(id, ae_foodname, ae_diettype);
			System.out.println("삽입 결과 : "+ saveYN);
		}else if(checkdiet == 1){
			System.out.println("들어왔는지 체크");
			int updateYN = service.updateActuality(id, ae_foodname, ae_diettype);
			System.out.println("업데이트 결과 : "+ updateYN);
		}else {
			System.out.println("null이나 undefined 말고는.. 여기 들어올 이유가 없는데");
		}
		List<ActualityEatingDto> list = service.dailyActuality(id);
//		for(ActualityEatingDto l : list) {
//			System.out.println("가져온 Id 값은?"+l.getId());
//		}		
		return list;
	}
	
	@GetMapping("/Actuality/dalilyNutri.do") //조회
	public List<ActualityEatingDto> getDailyNutri(String id){
		List<ActualityEatingDto> list = service.dailyActuality(id);
		return list;
	}
	
	//객체 탐지 음식 가져오기
	@PostMapping("/getEatting.do")
	public List getEatting(@RequestBody Map<String, Object> map) {
		List record = new ArrayList();
		record = service.getEatting(map.get("id").toString());
		
		return record;
	}
}