package com.ssafy.gotcha.config;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.ssafy.gotcha.api.service.PlayerServiceImpl;

@Component
public class WebSocketEventListener {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

	@Autowired
	private PlayerServiceImpl playerService;

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String connectionId = headerAccessor.getSessionId();
		logger.info("Client connected with connectionId: {}", connectionId);
		GenericMessage generic = (GenericMessage) headerAccessor.getHeader("simpConnectMessage");
	    Map nativeHeaders = (Map) generic.getHeaders().get("nativeHeaders");
        String roomId = (String) ((List) nativeHeaders.get("roomId")).get(0);
        String userId =(String) ((List) nativeHeaders.get("userId")).get(0);
		playerService.connectPlayer(connectionId, roomId, userId);
	}

	// 웹소켓 세션에서 사용자 이름을 추출하고 연결된 모든 클라이언트에게 사용자 퇴장 이벤트를 broadcast
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String connectionId = headerAccessor.getSessionId();
		logger.info("Client disConnected with connectionId: {}", connectionId);
		playerService.disConnectPlayer(connectionId);
	}
}
