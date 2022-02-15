package com.ssafy.gotcha.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.messaging.support.ChannelInterceptor;
import com.ssafy.gotcha.api.service.PlayerServiceImpl;

@Component
public class StompHandler implements ChannelInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(StompHandler.class);

	@Autowired
	private PlayerServiceImpl playerService;
	
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
    	StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        String connectionId = headerAccessor.getSessionId();
        switch (headerAccessor.getCommand()) {
        	// 유저가 구독하면 해당 게임 세션에 플레이어 객체 추가
        	case SUBSCRIBE:
	        	break;
            case CONNECT:
            	logger.info("Client connected with connectionId: {}", connectionId);
            	String roomId = headerAccessor.getFirstNativeHeader("roomId");
            	String userId = headerAccessor.getFirstNativeHeader("userId");
            	playerService.connectPlayer(connectionId, roomId, userId);
                break;
            case DISCONNECT:
            	logger.info("Client disConnected with connectionId: {}", connectionId);
            	playerService.disConnectPlayer(connectionId);            	
                break;
            default:
                break;
        }

    }
}