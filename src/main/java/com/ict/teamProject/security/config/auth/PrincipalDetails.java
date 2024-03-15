package com.ict.teamProject.security.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.ict.teamProject.member.service.MemberDto;

import lombok.Data;

//시쿠리티가 /login 주소요청이 오면 낚아채서 로그인을 진행
//로그인이 완료되면 시큐리티session생성 
//오브젝트 타입 => Authentication 타입 객체
//Authentication 안에 User정보가 있어야됨.
// User 오브젝트타입 => UserDetails타입 객체

//Security Session => Authentication => UserDetails(PrincipalDetails)
@Data
public class PrincipalDetails implements UserDetails,OAuth2User {

	private MemberDto user;	
	private Map<String,Object> attributes;
	
	public PrincipalDetails(MemberDto user) {
		this.user = user;
	}
	
	public PrincipalDetails(MemberDto user,Map<String,Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	//해당 유저의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    Collection<GrantedAuthority> collect = new ArrayList<>();
	    String authority = user.getAuthority(); // getAuthority 메소드를 이용해 사용자의 권한을 가져옴
	    collect.add(new SimpleGrantedAuthority(authority)); // 가져온 권한을 부여
	    return collect;
	}

	@Override
	public String getPassword() {
		return user.getPwd();
	}

	@Override
	public String getUsername() {
		return user.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}


	@Override
	public String getName() {
		// 안중요함
		return null;
	}
	
}
