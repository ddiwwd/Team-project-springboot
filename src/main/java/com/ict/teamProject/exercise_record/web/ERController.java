package com.ict.teamProject.exercise_record.web;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.ict.teamProject.exercise_record.ERDto;
import com.ict.teamProject.exercise_record.ERService;





@Controller
@RequestMapping("/exer")
@RestController
@CrossOrigin(origins = "http://localhost:3333")
public class ERController {
	
	//서비스 주입
	@Autowired
	private ERService<ERDto> service;
	
	@PostMapping("/getData.do")
	@ResponseBody
	public List getData(@RequestBody Map map) {
		System.out.println("운동 아이디는?"+ map.get("id").toString());
		String id = map.get("id").toString();
		ERDto dto = new ERDto();
		List<ERDto> re = new ArrayList<ERDto>();
		re = service.getData(id);
		for(ERDto p : re) {
			System.out.println("운동 데이터"+p.getEName());
			System.out.println("운동 데이터"+p.getId());
			System.out.println("운동 데이터"+p.getEType());

		}
		System.out.println("운동 데이터"+re);
		return re;
	}
}
