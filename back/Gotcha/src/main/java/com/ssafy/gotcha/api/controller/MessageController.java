package com.ssafy.gotcha.api.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
	
}
