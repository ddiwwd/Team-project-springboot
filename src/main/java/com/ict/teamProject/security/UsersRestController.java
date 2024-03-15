/*package com.ict.teamProject.security;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersRestController {

	@Autowired
	private OnememoMapper mapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@PostMapping("/users")
	private Map insertUser(@RequestBody Map<String,String> map) {
		
		//사용자가 입력한 비밀번호를 암호화
		String rawPassword=map.get("pwd");
		String encodedPassword=passwordEncoder.encode(rawPassword);//원문을 암호화
		//암호화된 비밀번호로 다시 설정
		map.put("pwd", encodedPassword);
		System.out.println("비밀번호 원문:"+rawPassword);
		System.out.println("암호화된 비밀번호:"+encodedPassword);
		System.out.println("암호일치여부 판단"+passwordEncoder.matches(rawPassword, encodedPassword));
		int affected=mapper.saveUser(map);	
		System.out.println(affected+"행이 입력되었어요");
		return map;
	}
}
*/