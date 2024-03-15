package com.ict.teamProject.security.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ict.teamProject.member.service.MemberDetailService;
import com.ict.teamProject.member.service.MemberDto;
import com.ict.teamProject.member.service.MemberService;
import com.ict.teamProject.security.config.auth.PrincipalDetails;
import com.ict.teamProject.security.util.JWTTokens;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private MemberDetailService service;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		
		if( principalDetails.getUsername() == null) {
			System.out.println("소셜 principalDetails.getUsername() 가 null이다");
			return;
		}
		
		MemberDto dto = service.getMemberById(principalDetails.getUsername());
		boolean hasAdditionalInfo = (dto != null && dto.hasAdditionalInfo());
		
		Map<String,Object> payloads = new HashMap<>();
		payloads.put("username",principalDetails.getUsername());
		long expirationTime = 1000 * 60 * 60 * 1;//1시간
		JWTTokens tokens = new JWTTokens();
		String token = tokens.createToken(principalDetails.getUsername(), payloads, expirationTime);
		
		Cookie cookie = new Cookie("User-Token", token                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          );
		cookie.setHttpOnly(true);
		cookie.setMaxAge((int)expirationTime);
		cookie.setPath("/");
		cookie.setSecure(true);
		response.addCookie(cookie);

		
		 // 추가 정보가 있는 경우와 없는 경우에 따라 다른 URL로 리다이렉트
        if (hasAdditionalInfo) {
            response.sendRedirect("http://localhost:3333/AuthCallback");
            System.out.println("일반도 있음?");
        } else {
        	System.out.println(dto);
            response.sendRedirect("http://localhost:3333/registersocial");
        }
	}
}
