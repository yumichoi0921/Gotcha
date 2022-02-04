package com.saffy.gotcha.config;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").withSockJS(); // withSockJS(): 브라우저에서 websocket을 지원하지 않을 경우 fallback 옵션을 활성화 하는데 사용
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/pub");	// Client에서 SEND 요청을 처리
		registry.enableSimpleBroker("/sub"); // destination 헤더가 '/sub'으로 시작하는 메세지를 브로커로 라우팅한다.
	}
}
