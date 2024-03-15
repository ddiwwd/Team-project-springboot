package com.ict.teamProject.chat.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.teamProject.bbs.service.BBSDto;
import com.ict.teamProject.bbs.service.BBSService;
import com.ict.teamProject.bbs.service.BBSUsersProfileDto;
import com.ict.teamProject.bbs.service.LikesDto;
import com.ict.teamProject.chat.service.ChatDto;
import com.ict.teamProject.chat.service.ChatService;
import com.ict.teamProject.command.FileUtils;
import com.ict.teamProject.files.service.FilesDto;


@RequestMapping("/chat")
@RestController
@CrossOrigin(origins = "http://localhost:3333")
public class ChatController {
	
	//서비스 주입
	@Autowired
	private ChatService<ChatDto> service;
	
	//입력처리]
	@PostMapping("/SoloWrite.do")
	@ResponseBody
	public int writeOk(@RequestBody Map map) {
		int affected = 0;
		System.out.println("id:"+map.get("id"));
		System.out.println("ruser:"+map.get("ruser"));
		System.out.println("content:"+map.get("content"));
		
		ChatDto dto = new ChatDto();
		affected = service.insert(map);
		
		if(affected==0) {//입력 실패
			return affected;
		}
		return affected;
	}/////
	

	@RequestMapping(value="/selectChat.do")
	@ResponseBody
	public ChatDto selectChat(@RequestBody Map map) {
		System.out.println("접속한 사람:"+map.get("id").toString());
		System.out.println("내가 보낼 사람:"+map.get("ruser").toString());
		
		//서비스 호출
		ChatDto record= service.selectChat(map);
		//줄바꿈
		if (record != null) {
			record.setContent(record.getContent().replace("\r\n", "<br/>"));
		}
		System.out.println("record-----"+record);
		//뷰정보 반환
		return record;
	}
	
	@RequestMapping(value="/whoChating.do")
	@ResponseBody
	public ChatDto whoChating(@RequestBody String id) {
		System.out.println("접속한 사람:"+id);
		
		//서비스 호출
		ChatDto record= service.whoChating(id);
		//줄바꿈
		if (record != null) {
			record.setContent(record.getContent().replace("\r\n", "<br/>"));
		}
		System.out.println("record-----"+record);
		//뷰정보 반환
		return record;
	}
	
	@RequestMapping(value="/allChating.do")
	@ResponseBody
	public List<ChatDto> allChating(@RequestParam String id) {
	    System.out.println("접속한 사람:"+id);
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 시간을 포함한 형식
	    //서비스 호출
	    List<ChatDto> records = service.allChating(id);
	    //줄바꿈
	    for (ChatDto record : records) {
	    	if (record != null && record.getContent() != null) {
	    	    record.setContent(record.getContent().replace("\r\n", "<br/>"));
	    	}
	        if (record != null && record.getSendDate() != null) {
	            Timestamp timestamp = Timestamp.valueOf(record.getSendDate());
	            String formattedDate = format.format(timestamp);
	            record.setSendDate(formattedDate); 
	        }
	    	System.out.println("record-----"+record.getId());
	    	System.out.println("record-----"+record.getRuser());
	        System.out.println("record-----"+record.getContent());
	        System.out.println("record-----"+record.getSendDate());
	    }
	    //뷰정보 반환
	    return records;
	}
	
	//메이트방 챗 가져오기
	@RequestMapping(value="/allMateChating.do")
	@ResponseBody
	public List<ChatDto> allMateChating(@RequestBody Map map) {
		System.out.println("단체채팅 들어왓나??"+ map.get("mateNo"));
		
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 시간을 포함한 형식
	    //서비스 호출
	    List<ChatDto> records = service.allMateChat(map);
	    //줄바꿈
	    for (ChatDto record : records) {
	    	if (record != null && record.getContent() != null) {
	    	    record.setContent(record.getContent().replace("\r\n", "<br/>"));
	    	}
	        if (record != null && record.getSendDate() != null) {
	            Timestamp timestamp = Timestamp.valueOf(record.getSendDate());
	            String formattedDate = format.format(timestamp);
	            record.setSendDate(formattedDate); 
	        }
	    	System.out.println("record-----"+record.getId());
	    	System.out.println("record-----"+record.getRuser());
	        System.out.println("record-----"+record.getContent());
	        System.out.println("record-----"+record.getSendDate());
	    }
	    //뷰정보 반환
	    return records;
	}
	
	//메이트 채팅 입력]
	@PostMapping("/mateWrite.do")
	@ResponseBody
	public int mateChatIn(@RequestBody Map map) {
		int affected = 0;
		System.out.println("id:"+map.get("id"));
		System.out.println("ruser:"+map.get("ruser"));
		System.out.println("content:"+map.get("content"));
		System.out.println("mateNo:"+map.get("mateNo"));
		
		ChatDto dto = new ChatDto();
		affected = service.mateIn(map);
		
		if(affected==0) {//입력 실패
			return affected;
		}
		return affected;
	}/////
	
	//챌린지방 챗 가져오기
	@RequestMapping(value="/allChallChating.do")
	@ResponseBody
	public List<ChatDto> allChallChating(@RequestBody Map map) {
		System.out.println("단체채팅 들어왓나??"+ map.get("mateNo"));
		
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 시간을 포함한 형식
	    //서비스 호출
	    List<ChatDto> records = service.allChallChat(map);
	    //줄바꿈
	    for (ChatDto record : records) {
	    	if (record != null && record.getContent() != null) {
	    	    record.setContent(record.getContent().replace("\r\n", "<br/>"));
	    	}
	        if (record != null && record.getSendDate() != null) {
	            Timestamp timestamp = Timestamp.valueOf(record.getSendDate());
	            String formattedDate = format.format(timestamp);
	            record.setSendDate(formattedDate); 
	        }
	    	System.out.println("record-----"+record.getId());
	    	System.out.println("record-----"+record.getRuser());
	        System.out.println("record-----"+record.getContent());
	        System.out.println("record-----"+record.getSendDate());
	    }
	    //뷰정보 반환
	    return records;
	}
	
	//챌린지방 채팅 입력]
	@PostMapping("/challWrite.do")
	@ResponseBody
	public int challChatIn(@RequestBody Map map) {
		int affected = 0;
		System.out.println("id:"+map.get("id"));
		System.out.println("ruser:"+map.get("ruser"));
		System.out.println("content:"+map.get("content"));
		System.out.println("mateNo:"+map.get("mateNo"));
		
		ChatDto dto = new ChatDto();
		affected = service.challIn(map);
		
		if(affected==0) {//입력 실패
			return affected;
		}
		return affected;
	}/////


	

}
