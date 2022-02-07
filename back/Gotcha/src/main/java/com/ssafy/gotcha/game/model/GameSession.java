package com.ssafy.gotcha.game.model;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameSession {
	private String gameSessionId; // 현재 게임방의 id
	private HashMap<String, Player> players; // 현재 게임방에 참가한 플레이어 리스트
	private String hostId;
//	private GameManager gameMgr; // 선택된 게임을 진행할 Mgr.  TODO: 추가해야함.

	public GameSession(String gameSessionId, Player hostPlayer) {
		this.gameSessionId = gameSessionId;
		players = new HashMap<String, Player>();
		players.put(hostPlayer.getUserId(), hostPlayer); // UUID , ��������
	}

// TODO: 구현해야함
//	public void setGameManager(String TYPE) {
//		switch (TYPE) {
//		case "DODUK":
//			gameMgr = new DodukGameManager(this.players);
//			break;
//		case "LIAR":
//			gameMgr = new LiarGameManager(this.players);
//			break;
//		}
//	}

}
