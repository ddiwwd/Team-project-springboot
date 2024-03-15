package com.ict.teamProject.security.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserAccessDeniedHandler implements AccessDeniedHandler{
	
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // 클라이언트에게 보낼 JSON 데이터
        String jsonData = "{\"message\": \"Access Denied\", \"redirectUrl\": \"http://localhost:3333/login\"}";
        System.out.println("작동하니?");
        response.getWriter().print(jsonData);
        response.getWriter().flush();
    }
}

