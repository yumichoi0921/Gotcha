package com.ssafy.gotcha.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.gotcha.game.model.ChatMessage;
import com.ssafy.gotcha.game.model.GameMessage;
import com.ssafy.gotcha.repository.GameSessionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MessageController {
	private final SimpMessagingTemplate template;
	
	//test code
	@Autowired
	private GameSessionRepository gameSessionRepository;
	
	@MessageMapping("/message")
	public void sendMessage(@Payload ChatMessage chatMessage) throws JsonProcessingException {
		String gameSessionId = chatMessage.getRoomId();
		switch (chatMessage.getType()) {
        case READY : 

            break;
        case START : 
        	// testcode
        	gameSessionRepository.findGameSessionById(gameSessionId).gameStart();
        	GameMessage gameMessage = gameSessionRepository.findGameSessionById(gameSessionId).toGameMessage();
        	ObjectMapper mapper = new ObjectMapper();
        	String objectToJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(gameMessage);
        	chatMessage.setContent(objectToJson);
            break;
        case GAME : 
//       
            break;
        default:
            break;
    }
		template.convertAndSend("/sub/" + chatMessage.getRoomId(), chatMessage);
		
//		@MessageMapping("/message/game")
//		public void sendMessage(@Payload gameMessage gameMessage) throws JsonProcessingException {
//			String gameSessionId = chatMessage.getRoomId();
//			switch (chatMessage.getType()) {
//	    	case JOIN:
////	        	System.out.println("join");
//	    		template.convertAndSend("/sub/" + chatMessage.getRoomId(), chatMessage);
//	        	break;
//	        case LEAVE:
//	            break;
//	        case CHAT : 
////	        	System.out.println("chat");
//	        	template.convertAndSend("/sub/" + chatMessage.getRoomId(), chatMessage);
//	            break;
//	        case READY : 
////	        	System.out.println("chat");
//	            break;
//	        case START : 
////	        	System.out.println("chat");
//	        	// testcode
//	        	gameSessionRepository.findGameSessionById(gameSessionId).gameStart();
//	        	GameMEssage gameMessage = gamese..togamesssage();
//	        	new GameMessage 
//	        	ObjectMapper mapper = new ObjectMapper();
//	        	String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(gameSessionRepository.findGameSessionById(gameSessionId));
//	        	chatMessage.setContent(jsonInString);
//	        	template.convertAndSend("/sub/" + chatMessage.getRoomId(), gameMessage);
//	            break;
//	        default:
//	            break;
//	    }
		
	}
	
}
