package com.ict.teamProject.security.handler;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // 클라이언트에게 보낼 JSON 데이터
        String jsonData = "{\"message\": \"Authentication Failed\", \"redirectUrl\": \"http://localhost:3333/login\"}";
        System.out.println("권한 없음");
        response.getWriter().print(jsonData);
        response.getWriter().flush();
    }

}
