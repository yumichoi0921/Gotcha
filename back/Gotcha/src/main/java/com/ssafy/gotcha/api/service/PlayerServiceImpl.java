package com.ssafy.gotcha.api.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.gotcha.game.model.Player;
import com.ssafy.gotcha.repository.GameSessionRepository;
import com.ssafy.gotcha.repository.PlayerRepository;

@Service
public class PlayerServiceImpl {
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private GameSessionRepository gameSessionRepository;
	
	public void connectPlayer(String connectionId, String roomId, String userId) {
		playerRepository.create(connectionId, roomId, userId);
		Player player = playerRepository.get(connectionId);
		gameSessionRepository.findGameSessionById(roomId).getPlayers().put(userId, player);
	}
	
	public void disConnectPlayer(String connectionId) {
		Player player = playerRepository.get(connectionId);
		String userId = player.getUserId();
		String gameSessionId = player.getRoomId();
		gameSessionRepository.findGameSessionById(gameSessionId).getCardList().remove(userId);
		gameSessionRepository.findGameSessionById(gameSessionId).getPlayers().remove(userId);
		playerRepository.remove(connectionId);	
	}

}
