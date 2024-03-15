package com.ict.teamProject.member_history.web;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ict.teamProject.member_history.service.MemberHistoryDto;
import com.ict.teamProject.member_history.service.MemberHistoryService;
import com.ict.teamProject.files.service.FilesDto;


@Controller
@RequestMapping("/memberhistory")
@CrossOrigin(origins = "http://localhost:3333")
public class MemberHistoryController {
	
	//서비스 주입
	@Autowired
	private MemberHistoryService<MemberHistoryDto> service;

	
	//상세보기]
	@RequestMapping(value="/View.do",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<MemberHistoryDto> view(@RequestParam Map map) {
		//서비스 호출
		List<MemberHistoryDto> record= service.selectAll(map);
		//뷰정보 반환
		return record;
	}///////////////////
}
