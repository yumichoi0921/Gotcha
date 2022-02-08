package com.ssafy.gotcha.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.ssafy.gotcha.game.model.ChatMessage;
import com.ssafy.gotcha.game.model.GameSession;
import com.ssafy.gotcha.game.model.Player;

@Repository
public class PlayerRepository {
	private Map<String, Player> players;

	@PostConstruct
	private void init() {
		players = new LinkedHashMap<>();
	}
	
    public void create(String connectionId, String roomId, String userId) {
    	Player player = Player.builder()
				.userId(userId)
				.nickName(userId)
				.readyState(false)
				.isActivated(true)
				.connectionId(connectionId)
				.roomId(roomId)
				.build();
    	players.put(connectionId, player);
    }

    public void remove(String connectionId) {
        String roomId = players.get(connectionId).getRoomId();
        players.remove(connectionId); 
    }
    
    public Player get(String connectionId) {
    	return players.get(connectionId); 
    }

}
