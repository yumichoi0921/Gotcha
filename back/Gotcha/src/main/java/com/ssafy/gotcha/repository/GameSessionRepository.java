package com.ssafy.gotcha.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.ssafy.gotcha.game.model.GameSession;
import com.ssafy.gotcha.game.model.Player;

@Repository
public class GameSessionRepository {
	private Map<String, GameSession> gameSessionMap;

	@PostConstruct
	private void init() {
		gameSessionMap = new LinkedHashMap<>();
		// testcode -> 게임세션 생성
		gameSessionMap.put("865a1de0-c25b-4819-a7bc-cc87fe19272c", new GameSession("865a1de0-c25b-4819-a7bc-cc87fe19272c", "host"));
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

	public GameSession createGameSession(String gameSessionId, String hostId) {
		GameSession gameSession = new GameSession(gameSessionId, hostId);
		gameSessionMap.put(gameSession.getGameSessionId(), gameSession);

		return gameSession;
	}
}
