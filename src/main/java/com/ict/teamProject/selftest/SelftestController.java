package com.ict.teamProject.selftest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


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

import com.ict.teamProject.selftest.dto.AllergyInfoDto;
import com.ict.teamProject.selftest.dto.HateFoodInfoDto;
import com.ict.teamProject.selftest.dto.InbodyInfoDto;
import com.ict.teamProject.selftest.dto.MemberAllergyDto;
import com.ict.teamProject.selftest.dto.MemberHateFoodDto;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import java.nio.file.StandardCopyOption;

@RestController
@CrossOrigin(origins = "http://localhost:3333")
public class SelftestController {
	SelftestService service;
	public SelftestController(SelftestService service) {
		this.service = service;
	}
	
	@GetMapping("/Allergy/ListView.do") //조회
	public List<AllergyInfoDto> findAllList(){
		List<AllergyInfoDto> allergyInfolist = service.findAllAllergy();
		System.out.println("아웃풋 : "+allergyInfolist);
		return allergyInfolist;
	}
	
	@GetMapping("/HateFood/ListView.do") //조회
	public List<HateFoodInfoDto> findAllHateFood(){
		List<HateFoodInfoDto> hatefoodInfolist = service.findAllHateFood();
		System.out.println("아웃풋 : "+hatefoodInfolist);
		return hatefoodInfolist;
	}
	
	@PostMapping("/SaveMember/Allergy")
	public void saveMemberAllergy(@RequestBody Map map) {	    
		System.out.println("들어온값:"+map);
		String id = (String) map.get("id");
		int affected = service.deleteAllAllergy(id);
		String allergies = (String) map.get("allergies");
		String[] allergyArray = allergies.split(",");
	    for (String allergy : allergyArray) {
	    	int allergyInt = Integer.parseInt(allergy.trim());
	        service.saveMemberAllergy(id, allergyInt);
	    }
	}
	
	@PostMapping("/SaveMember/HateFood")
	public void saveMemberHateFood(@RequestBody Map map) {	    
		System.out.println("들어온값:"+map);
		String id = (String) map.get("id");
		int affected = service.deleteAllHateFood(id);
		String hatefoods = (String) map.get("hatefoods");
		String[] hatefoodArray = hatefoods.split(",");
		System.out.println("싫어하는 음식 목록:"+hatefoodArray);
	    for (String hatefood : hatefoodArray) {
	    	int hatefoodInt = Integer.parseInt(hatefood.trim());
	        service.saveMemberHateFood(id, hatefoodInt);
	    }
	}
	
	@GetMapping("/GetMember/Allergy")
	public List getuserAllergy(@RequestParam String id) {
		System.out.println("들어온 id:"+id);
		List<MemberAllergyDto> list = service.getMemberAllergy(id);
		List<AllergyInfoDto> allergylist = new ArrayList<>();

		for (MemberAllergyDto dto : list) {
		    String allergyNo = dto.getAllergy_no();
		    System.out.println(allergyNo);
		    List<AllergyInfoDto> foundAllergy = service.findAllergy(allergyNo);
		    System.out.println("꺼내온 알러지 정보" + foundAllergy);
		    allergylist.addAll(foundAllergy);
		}
		System.out.println(allergylist);
		return allergylist;
	}
	
	@GetMapping("/GetMember/HateFood")
	public List getuserHateFood(@RequestParam String id) {
		System.out.println("들어온 id:"+id);
		List<MemberHateFoodDto> list = service.getMemberHateFood(id);
		List<HateFoodInfoDto> hatefoodlist = new ArrayList<>();

		for (MemberHateFoodDto dto : list) {
		    String hatefoodNo = dto.getHatefood_no();
		    List<HateFoodInfoDto> foundHateFood = service.findHateFood(hatefoodNo);
		    System.out.println("꺼내온 싫은 음식  정보" + foundHateFood);
		    hatefoodlist.addAll(foundHateFood);
		}
		System.out.println(hatefoodlist);
		return hatefoodlist;
	}
	
	@PostMapping("/Inbody/Save.do")
	public int saveInbody(@RequestBody Map map) {	    
		System.out.println("들어온 인바디 값:"+map);
		int affected = service.saveInbody(map);
		return affected;
	}
	
	@PostMapping("/Inbody/findinbody.do")
	public InbodyInfoDto findinbody(@RequestBody Map map) {	    
		System.out.println("들어온 아이디 값:"+map.get("id").toString());
		InbodyInfoDto dto = service.findinbody(map.get("id").toString());
		System.out.println("인바디 잘 나오는지 확인"+dto.getInb_date());
		return dto;
	}
	
	@PostMapping("/Inbody/findAllInbody.do")
	public List<InbodyInfoDto> findAllInbody(@RequestBody Map map) {	    
		System.out.println("들어온 아이디 값:"+map.get("id").toString());
		List<InbodyInfoDto> dto = service.findAllInbody(map.get("id").toString());
		return dto;
	}
}