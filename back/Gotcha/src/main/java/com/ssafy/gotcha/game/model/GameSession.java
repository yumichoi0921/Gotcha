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
	private String nowTurn;	// 현재 순서
	private List<String> winner;	// 카드를 다 버린 플레이어
	private List<String> candidate;	// 카드를 가지고 있는 플레이어

	public GameSession(String gameSessionId, Player hostPlayer) {
		this.gameSessionId = gameSessionId;
		players = new HashMap<String, Player>();
//		players.put(hostPlayer.getUserId(), hostPlayer); TODO: host입장 할 때 connectionId를 알 수 없어서 일단 보류.. 방생성과 openVidu 세션생성 순서 제대로 정의되면 추후 수정
	}

	// test code
	public void gameStart() {
		initCard();
		initTurn();
		initWinnerAndCandidate();
	}

	private void initWinnerAndCandidate() {
		winner = new ArrayList<String>();
		candidate = new ArrayList<String>();
		for (String userId : players.keySet()) {
			candidate.add(userId);
		}
	}

	private void initTurn() {
		// 순서 리스트
		turn = new ArrayList<String>();
		for (String userId : players.keySet()) {
			turn.add(userId);
		}
		// 첫번째 순서
		nowTurn = turn.get(0);
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
				"C5", "C6", "C7", "C8", "C9", "C10", "JOCKER");
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
				.nowTurn(nowTurn)
				.players(players)
				.cardList(cardList)
				.winner(winner)
				.candidate(candidate)
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
