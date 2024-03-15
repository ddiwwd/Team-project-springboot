package com.ict.teamProject.member.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ict.teamProject.member.service.MemberDto;
import com.ict.teamProject.member.service.MemberManageDto;

@Mapper
public interface MemberMapper {
	
	int saveMember(MemberDto dto);
	
	 boolean checkPhoneNumber(String tel);
	 
	 //레코드 하나
	 MemberDto findByMember(String id);
	 
	 int updateMember(String id, String colname, String newcolval);
	 
	 int logincheck(String id, String pwd);



	String findbyUserPassword(Map map);
	Map<String, Object> getInfo(String id);
	int isPreviousUser(String id);
	
	MemberDto findByUsername(String id);
	void joinSocialMember(MemberDto dto);
	MemberDto findByMemberInfo(String id);
	
	//회원가입
	void profileImageTable(MemberDto dto);

	Map searchPoint(String id);

	 
	void updateUser(MemberDto userEntity);
	 
	int updateSocialUser(MemberDto dto);

	 MemberDto findByMemberId(String id);

	MemberDto findMemberById(String id);

	MemberDto getMemberByNameAndBday(String name, String b_day);

	MemberDto getMemberByIdAndEmail(String id);

	int updatePassword(String id, String pwd);
	 

	 int saveFMCToken(Map map); //사용자의 FMC 토큰 저장
	 
	 void deleteFMCToken(String id); //사용자의 FMC 토큰 삭제
	 
	 String findFMCTokenById(String id); //사용자의 FMC 토큰 불러오기
	Map getUserAddress(String id);

	MemberDto findByUsernameAndRole(String username, String authority);

	List<Map<String, Object>> findAllUser();

	List<MemberManageDto> findAllComplainedUsers();

	void deleteUserFromComplainList(String id); //컴플레인 리스트에서 삭제
	
	void addUserToComplainList(MemberManageDto dto); //컴플레인 리스트에 등록

	String defaultArea(String id);
}
