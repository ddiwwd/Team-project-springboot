package com.ict.teamProject.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ict.teamProject.member.service.impl.MemberMapper;

@Service
public class RegisterService {
    private MemberMapper mapper;
    private PasswordEncoder passwordEncoder;
    
    public RegisterService(MemberMapper mapper) {
        this.mapper = mapper;
        this.passwordEncoder = new BCryptPasswordEncoder();  // 여기서 초기화
    }
    
    public int join(MemberDto member) {
        
        member.setAuthority("ROLE_USER");
        String rawPassword = member.getPwd();
        String encPassword = passwordEncoder.encode(rawPassword);
        member.setPwd(encPassword);
        System.out.println(member);
        return mapper.saveMember(member);
    }
}
