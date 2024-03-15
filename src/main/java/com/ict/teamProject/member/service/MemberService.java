package com.ict.teamProject.member.service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ict.teamProject.member.service.impl.MemberMapper;

import oracle.jdbc.OracleDatabaseException;

@Service
public class MemberService {
	private MemberMapper mapper;

	
	public MemberService(MemberMapper mapper) {
		this.mapper=mapper;
	}
	
	public int join(MemberDto dto) {
		return mapper.saveMember(dto);
	}
	
	
	public MemberDto selectdata(String id) {
		return mapper.findByMember(id); //MemberMapper 인터페이스의 findByMember메소드 호출
	}
	
	public int updatedata(String id, String colname, String newcolval) {	
		return mapper.updateMember(id, colname, newcolval);		
	}
	
	public int logincheck(String id, String pwd) {
		return mapper.logincheck(id, pwd);
	}

	public Map searchPoint(String id) {
		return mapper.searchPoint(id);
	}
	
	
    public MemberDto getMemberById(String id) {
        return mapper.findByMemberId(id);
    }
	
	
	public int updateSocialUser(MemberDto dto) {
		return mapper.updateSocialUser(dto);
	
	}

	public Map getUserAddress(String id) {
		return mapper.getUserAddress(id);
	}

//	public int updateSocialUser(MemberDto dto) {
//		return mapper.updateSocialUser(dto);
//	
//	}
	
	public void saveFMCToken(Map map) {
		if(findFMCTokenById(String.valueOf(map.get("id"))) != null) { //id에 대한 토큰 값이 있다면 기존의 값을 삭제
			mapper.deleteFMCToken(String.valueOf(map.get("id")));
		}
		mapper.saveFMCToken(map); 
	}
	
	public String findFMCTokenById(String id) {
		return mapper.findFMCTokenById(id);
	}
	public MemberDto getMemberByNameAndBday(String name, String b_day) {

		return mapper.getMemberByNameAndBday(name , b_day);
	}

	public MemberDto getMemberByIdAndEmail(String id) {
		
		return mapper.getMemberByIdAndEmail(id);
	}

	public int updateMemberPwd(String id, String newPwd) {
		BCryptPasswordEncoder bcy = new BCryptPasswordEncoder();
		String pwd = bcy.encode(newPwd);
		return mapper.updatePassword(id, pwd);
	}

	public List<Map<String, Object>> findAllUser() {
		return mapper.findAllUser();
	}


	public List<MemberManageDto> findAllComplainedUsers() {
		return mapper.findAllComplainedUsers();
	}

	public void deleteUserFromComplainList(String id) {
		mapper.deleteUserFromComplainList(id);
	}
	
	public void addUserToComplainList(List<MemberManageDto> dtos) {
		for(MemberManageDto dto : dtos) {
			mapper.addUserToComplainList(dto);
		}
	}

	public String defaultArea(String id) {
		return mapper.defaultArea(id);
	}
}
