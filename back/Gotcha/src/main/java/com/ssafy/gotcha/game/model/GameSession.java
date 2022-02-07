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
//		players.put(hostPlayer.getUserId(), hostPlayer); TODO: host입장 할 때 connectionId를 알 수 없어서 일단 보류.. 방생성과 openVidu 세션생성 순서 제대로 정의되면 추후 수정
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
