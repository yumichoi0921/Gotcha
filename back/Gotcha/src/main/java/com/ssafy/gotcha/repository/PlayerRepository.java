package com.ssafy.gotcha.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.ssafy.gotcha.game.model.Player;

@Repository
public class PlayerRepository {
	private Map<String, Player> players;

	@PostConstruct
	private void init() {
		players = new LinkedHashMap<>();
	}
	
    public Player create(String connectionId, String roomId, String userId) {
    	Player player = Player.builder()
				.userId(userId)
				.nickName(userId)
				.readyState(false)
				.isActivated(true)
				.connectionId(connectionId)
				.roomId(roomId)
				.build();
    	players.put(connectionId, player);
    	return player;
    }

    public void remove(String connectionId) {
        players.remove(connectionId); 
    }
    
    public Player get(String connectionId) {
    	return players.get(connectionId); 
    }

}
