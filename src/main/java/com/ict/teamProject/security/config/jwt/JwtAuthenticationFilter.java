package com.ict.teamProject.security.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ict.teamProject.security.config.auth.PrincipalDetails;
import com.ict.teamProject.security.util.JWTTokens;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager=authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		System.out.println("JwtAuthenticationFilter : 로그인 시도중");
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(id,pwd);
		
		System.out.println("attemptAuthentication 실행  ");
		Authentication authenticated = authenticationManager.authenticate(authenticationToken);
		PrincipalDetails principalDetails = (PrincipalDetails) authenticated.getPrincipal();
		System.out.println(principalDetails.getUsername());
		
		return authenticated;
		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
	        Authentication authResult) throws IOException, ServletException {

	    PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
	    System.out.println("principalDetails :" + principalDetails);
	    
	    Map<String, Object> payloads = new HashMap<>();
	    payloads.put("username", principalDetails.getUsername());
	    payloads.put("authority", principalDetails.getAuthorities().iterator().next().getAuthority());

	    long expirationTime = 1000 * 60 * 60 * 24;

	    JWTTokens tokens = new JWTTokens();
	    String token = tokens.createToken(principalDetails.getUsername(), payloads, expirationTime);

	    System.out.println("jwtAuthentication:" + principalDetails.getUsername());
	    Cookie cookie = new Cookie("User-Token", token);
	    cookie.setHttpOnly(true);
	    cookie.setMaxAge((int)expirationTime);
	    cookie.setPath("/");
	    cookie.setSecure(true);

	    response.addCookie(cookie);

	    // 응답 데이터를 클라이언트로 전송
	 // 사용자 이름과 권한을 응답 데이터로 추가
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("username", principalDetails.getUsername());
	    responseData.put("authority", principalDetails.getAuthorities().iterator().next().getAuthority());
	    responseData.put("token", token);
	    ObjectMapper objectMapper = new ObjectMapper();
	    String responseDataJson = objectMapper.writeValueAsString(responseData);
	    response.getWriter().write(responseDataJson);
	    
	    // 모든 사용자에게 쿠키를 보냅니다.
	    response.addCookie(cookie);
	    System.out.println(principalDetails.getUsername() + "쿠키 생성 되니?" + cookie);

	    // 권한이 'ROLE_ADMIN'인 경우에만 HTTP 응답 본문에 토큰을 넣어 보냅니다.
	    if ("ROLE_ADMIN".equals(payloads.get("authority"))) {
	        response.getWriter().write(token);
	    }
	}
	
	
}
