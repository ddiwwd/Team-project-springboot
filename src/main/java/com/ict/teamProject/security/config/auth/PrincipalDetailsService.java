
package com.ict.teamProject.security.config.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ict.teamProject.member.service.MemberDetailService;
import com.ict.teamProject.member.service.MemberDto;

// 시큐리티 설정에서 loginProcessingUrl("login")
//login 요청이 오면 자동으로 UserDetailsService 타입으로 loC 되어 있는 loadUserByUsername 함수가 실행

@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private MemberDetailService service;

	@Override 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    System.out.println("Username: " + username);
	    MemberDto userEntity = service.findByUsername(username);
	    if(userEntity != null) {
	        // 사용자의 권한을 확인
	        String authority = userEntity.getAuthority();

	        // 관리자 권한인 경우
	        if ("ROLE_ADMIN".equals(authority)) {
	            System.out.println("관리자 로그인");
	            // 추가적인 관리자 권한 처리를 여기에 작성할 수 있습니다.

	            return new PrincipalDetails(userEntity);
	        } else { // 일반 사용자 권한인 경우
	            System.out.println("일반 사용자 로그인");
	            return new PrincipalDetails(userEntity);
	        }
	    }
	    throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
	}



	
}
