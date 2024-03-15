package com.ict.teamProject.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration//외부 빈
@EnableWebSocket // 웹소켓 쓰기 위한 어노테이션
public class WebSocketConfig implements WebSocketConfigurer{
	
	//우리가 만든 웹소켓 서버를 생성자 인젝션으로 초기화
	private final WebSocketServer webSocketServer;
	public WebSocketConfig(WebSocketServer webSocketServer) {
		this.webSocketServer = webSocketServer;
	}
	
	//클라이언트 접속을 위한 엔드 포인트 설정
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketServer, "/chat/**").setAllowedOrigins("*");

	}
}
