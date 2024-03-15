package com.ict.teamProject.selftest;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ict.teamProject.selftest.dto.AllergyInfoDto;
import com.ict.teamProject.selftest.dto.HateFoodInfoDto;
import com.ict.teamProject.selftest.dto.InbodyInfoDto;
import com.ict.teamProject.selftest.dto.MemberAllergyDto;
import com.ict.teamProject.selftest.dto.MemberHateFoodDto;
@Service
public class SelftestService {
	
	//mapper 생성자 주입
	private SelftestMapper mapper;
	public SelftestService(SelftestMapper mapper) {
		this.mapper = mapper;
	}
	
	//알러지 Data
	public List<AllergyInfoDto> findAllAllergy() {
		return mapper.findAllAllergy();
	}
	public List<HateFoodInfoDto> findAllHateFood() {
		return mapper.findAllHateFood();
	}
	//
	public int saveMemberAllergy(String id, int allergy_no) {
		return mapper.saveMemberAllergy(id, allergy_no);
	}
	public int saveMemberHateFood(String id, int hatefood_no) {
		return mapper.saveMemberHateFood(id, hatefood_no);
	}
	//
	public List<MemberAllergyDto> getMemberAllergy(String id){
		return mapper.getMemberAllergy(id);
	}
	public List<MemberHateFoodDto> getMemberHateFood(String id) {
		return mapper.getMemberHateFood(id);
	}
	//
	public List<AllergyInfoDto> findAllergy(String allergyNo) {
		return mapper.findAllergy(allergyNo);
	}
	public List<HateFoodInfoDto> findHateFood(String hatefoodNo) {
		return mapper.findHateFood(hatefoodNo);
	}
	//
	public int deleteAllAllergy(String id) {
		return mapper.deleteAllAllergy(id);
	}

	public int deleteAllHateFood(String id) {
		return mapper.deleteAllHateFood(id);
	}

	public int saveInbody(Map map) {
		return mapper.saveInbody(map);
	}

	public InbodyInfoDto findinbody(String string) {
		return mapper.findinbody(string);
		
	}

	public List<InbodyInfoDto> findAllInbody(String string) {
		return mapper.findAllInbody(string);
	}
}
