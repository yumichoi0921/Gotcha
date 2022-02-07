package com.ssafy.gotcha.api.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.gotcha.game.model.ChatMessage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MessageController {
	private final SimpMessagingTemplate template;

	@MessageMapping("/message")
	public void sendMessage(@Payload ChatMessage chatMessage) {
		template.convertAndSend("/sub/" + chatMessage.getRoomId(), chatMessage);
	}
    @MessageMapping("/message/join")
    // 사용자 참여 이벤트를 토픽을 구독한 모든 클라이언트에broadcast
    public void joinUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
    	headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    	template.convertAndSend("/sub/"+chatMessage.getRoomId(), chatMessage);
    }
    @MessageMapping("/message/leave")
    // 사용자 참여 이벤트를 토픽을 구독한 모든 클라이언트에broadcast
    public void leaveUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
    	headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    	template.convertAndSend("/sub/"+chatMessage.getRoomId(), chatMessage);
    }
	
}
