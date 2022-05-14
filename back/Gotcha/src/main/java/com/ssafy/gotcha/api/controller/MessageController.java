package com.ssafy.gotcha.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.gotcha.api.service.RoomService;
import com.ssafy.gotcha.game.model.ChatMessage;
import com.ssafy.gotcha.game.model.GameSession;
import com.ssafy.gotcha.game.model.MessageType;
import com.ssafy.gotcha.repository.GameSessionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MessageController {
	private final SimpMessagingTemplate template;

	@Autowired
	private GameSessionRepository gameSessionRepository;

	@Autowired
	private RoomService roomService;

	@MessageMapping("/message")
	public void sendMessage(@Payload ChatMessage chatMessage) throws JsonProcessingException {
		String gameSessionId = chatMessage.getRoomId();
		switch (chatMessage.getType()) {
		case START:
			GameSession gameSession = gameSessionRepository.findGameSessionById(gameSessionId);
			gameSession.gameStart();
			chatMessage.setType(MessageType.GAME);
			chatMessage.setContent(gameSession.toGameMessage().toContent());
			roomService.changeIsRun(gameSessionId, true);
			break;
		case END:
			roomService.changeIsRun(gameSessionId, false);
			break;
		default:
			break;
		}
		template.convertAndSend("/sub/" + chatMessage.getRoomId(), chatMessage);
	}

}
