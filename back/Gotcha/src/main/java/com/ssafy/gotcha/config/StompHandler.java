package com.ssafy.gotcha.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

@Component
public class StompHandler extends ChannelInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(StompHandler.class);

    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
    	StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        String sessionId = headerAccessor.getSessionId();
        switch (headerAccessor.getCommand()) {
        	case SUBSCRIBE:
	            // 유저가 Websocket으로 connect()를 한 뒤 호출됨
	        	System.out.println("구독");
	        	break;
            case CONNECT:
                // 유저가 Websocket으로 connect()를 한 뒤 호출됨
            	System.out.println("연결");
                break;
            case DISCONNECT:
                // 유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생함(페이지 이동~ 브라우저 닫기 등)
            	System.out.println(headerAccessor);
            	System.out.println(" 연결 끊김");
                break;
            default:
                break;
        }

    }
}