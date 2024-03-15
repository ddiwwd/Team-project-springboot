package com.ict.teamProject.selftest;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.selftest.dto.AllergyInfoDto;
import com.ict.teamProject.selftest.dto.HateFoodInfoDto;
import com.ict.teamProject.selftest.dto.MemberAllergyDto;
import com.ict.teamProject.selftest.dto.MemberHateFoodDto;
import com.ict.teamProject.selftest.dto.InbodyInfoDto;

@Mapper
public interface SelftestMapper {
	public List<AllergyInfoDto> findAllAllergy();
	public List<HateFoodInfoDto> findAllHateFood();
	public int saveMemberAllergy(String id, int allergy_no);
	public int saveMemberHateFood(String id, int hatefood_no);
	public List<MemberAllergyDto> getMemberAllergy(String id);
	public List<MemberHateFoodDto> getMemberHateFood(String id);
	public List<AllergyInfoDto> findAllergy(String allergyNo);
	public List<HateFoodInfoDto> findHateFood(String hatefoodNo);
	public int deleteAllAllergy(String id);
	public int deleteAllHateFood(String id);
	public int saveInbody(Map map);
	public InbodyInfoDto findinbody(String string);
	public List<InbodyInfoDto> findAllInbody(String string);	
}
