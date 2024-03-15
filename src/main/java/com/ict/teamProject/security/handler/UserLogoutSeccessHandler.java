package com.ict.teamProject.security.handler;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserLogoutSeccessHandler extends SimpleUrlLogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // 클라이언트에게 보낼 JSON 데이터
        String jsonData = "{\"message\": \"로그아웃 되었습니다.\", \"redirectUrl\": \"http://localhost:3333/login\"}";

        System.out.println("로그아웃");
        
        response.getWriter().print(jsonData);
        response.getWriter().flush();
    }
}
