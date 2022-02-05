package com.saffy.gotcha.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.saffy.gotcha.game.model.GameSession;
import com.saffy.gotcha.game.model.Player;

@Repository
public class GameSessionRepository {
	private Map<String, GameSession> gameSessionMap;

	@PostConstruct
	private void init() {
		gameSessionMap = new LinkedHashMap<>();
	}

	public List<GameSession> findAllGameSessions() {
		// 채팅방 생성 순서 최근 순으로 반환
		List<GameSession> result = new ArrayList<>(gameSessionMap.values());
		Collections.reverse(result);

		return result;
	}

	public GameSession findGameSessionById(String id) {
		return gameSessionMap.get(id);
	}

	public GameSession createGameSession(String gameSessionId, Player hostPlayer) {
		GameSession gameSession = new GameSession(gameSessionId, hostPlayer);
		gameSessionMap.put(gameSession.getGameSessionId(), gameSession);

		return gameSession;
	}
}
