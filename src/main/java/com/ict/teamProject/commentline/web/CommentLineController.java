
package com.ict.teamProject.commentline.web;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.ict.teamProject.commentline.service.CommentLineDto;
import com.ict.teamProject.commentline.service.CommentLineService;
import com.ict.teamProject.files.service.FilesDto;


@Controller
@RequestMapping("/commentline")
@CrossOrigin(origins = "http://localhost:3333")
public class CommentLineController {
	
	//서비스 주입
	@Autowired
	private CommentLineService<CommentLineDto> service;
	
	//상세보기]
	@RequestMapping(value="/View.do",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List view(@RequestParam Map map) {
		List<CommentLineDto> records = service.selectAll(map);
		return records;
	}///////////////////	
	
	//댓글 입력]
	@PostMapping(value="/Write.do")
	@ResponseBody
	public int input(@RequestParam Map map, int type) throws JsonMappingException{
		int affected = service.insert(map, type);
		return affected;
	}///////////////////
	
	//댓글 삭제]
	@DeleteMapping(value="/Delete.do")
	@ResponseBody
	public int delete(@RequestParam int c_no) throws JsonMappingException{
		System.out.println(c_no);
		int affected = service.delete(c_no);
		System.out.println(affected);
		return affected;
	}///////////////////
	
	//댓글 수정]
	@RequestMapping(value = "/Edit.do", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
	@ResponseBody
	public int update(@RequestParam String c_no, String ccomment) throws JsonMappingException{
		System.out.println(c_no);
		System.out.println(ccomment);
		int affected = service.update(c_no, ccomment);
		System.out.println(affected);
		return affected;
	}///////////////////
}