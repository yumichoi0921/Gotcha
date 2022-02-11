package com.ssafy.gotcha.game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameSession {
	private String gameSessionId; // 현재 게임방의 id
	private HashMap<String, Player> players; // 현재 게임방에 참가한 플레이어 리스트
	private String hostId;
//	private GameManager gameMgr; // 선택된 게임을 진행할 Mgr.  TODO: 추가해야함.
	// testcode;
	private HashMap<String, List<String>> cardList; // 플레이어별 카드 리스트
	private List<String> turn;	// 게임 순서 리스트
	private String picker;	// 뽑는 사람 id
	private String picked;	// 뽑히는 사람 id

	public GameSession(String gameSessionId, Player hostPlayer) {
		this.gameSessionId = gameSessionId;
		players = new HashMap<String, Player>();
//		players.put(hostPlayer.getUserId(), hostPlayer); TODO: host입장 할 때 connectionId를 알 수 없어서 일단 보류.. 방생성과 openVidu 세션생성 순서 제대로 정의되면 추후 수정
	}

	// test code
	public void gameStart() {
		initCard();
		initTurn();
	}

	private void initTurn() {
		// 순서 리스트
		turn = new ArrayList<String>();
		for (String userId : players.keySet()) {
			turn.add(userId);
		}
		// 첫번째 뽑는사람
		picker = turn.get(0);
		// 첫번째 뽑히는 사람
		picked = turn.get(turn.indexOf(picker)+1);
	}

	private void initCard() {
		// 플레이어별 카드덱 초기화
		cardList = new HashMap<String, List<String>>();
		for (String userId : players.keySet()) {
			cardList.put(userId, new ArrayList<String>());
		}
		// 카드 셔플
		List<String> cards = Arrays.asList("SA", "SJ", "SQ", "SK", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9",
				"S10", "HA", "HJ", "HQ", "HK", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "DA", "DJ", "DQ",
				"DK", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "CA", "CJ", "CQ", "CK", "C2", "C3", "C4",
				"C5", "C6", "C7", "C8", "C9", "C10", "JOKER");
		Collections.shuffle(cards);
		Queue<String> cardsQueue = new LinkedList<>(cards);
		// 카드 배분
		cardDistribution: while (!cardsQueue.isEmpty()) {
			for (String userId : cardList.keySet()) {
				if (cardsQueue.isEmpty())
					break cardDistribution;
				cardList.get(userId).add(cardsQueue.poll());
			}
		}
	}
	
	public GameMessage toGameMessage() {
		return GameMessage.builder()
				.gameSessionId(gameSessionId)
				.hostId(hostId)
				.turn(turn)
				.picker(picker)
				.picked(picked)
				.players(players)
				.cardList(cardList)
				.build();
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
