package com.ssafy.gotcha.game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

	private HashMap<String, List<Card>> cardList; // 플레이어별 카드 리스트
	private List<String> turn; // 게임 순서 리스트
	private String picker; // 뽑는 사람 id
	private String picked; // 뽑히는 사람 id
	private int timeCounter; // 타이머


	public GameSession(String gameSessionId, String hostId) {
		this.gameSessionId = gameSessionId;
		this.hostId = hostId;
		players = new HashMap<String, Player>();
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
		picked = turn.size() > 1? picked = turn.get(turn.indexOf(picker) + 1) : picker;
//		picked = turn.get(turn.indexOf(picker) + 1);
	}

	private void initCard() {
		// 플레이어별 카드덱 초기화
		cardList = new HashMap<String, List<Card>>();
		for (String userId : players.keySet()) {
			cardList.put(userId, new ArrayList<Card>());
		}
		// 카드 셔플
		String[] shape = { "S", "H", "D", "C" };
		String[] number = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "A", "J", "Q", "K" };
		List<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < number.length; j++) {
				cards.add(Card.builder().shape(shape[i]).number(number[j]).build());
			}
		}
		cards.add(Card.builder().shape("JOKER").number("JOKER").build());
		Collections.shuffle(cards);
		Queue<Card> cardsQueue = new LinkedList<>(cards);

		// 카드 배분 & 초기 중복 제거
		cardDistribution: while (!cardsQueue.isEmpty()) {
			for (String userId : cardList.keySet()) {
				if (cardsQueue.isEmpty())
					break cardDistribution;
				Card card = cardsQueue.poll();
				if (cardList.get(userId).contains(card)) {
					cardList.get(userId).remove(card);
				} else {
					cardList.get(userId).add(card);
				}
			}
		}
	}


	public GameMessage toGameMessage() {
		return GameMessage.builder().gameSessionId(gameSessionId).hostId(hostId).turn(turn).picker(picker)
				.picked(picked).players(players).cardList(cardList).timeCounter(30).build();
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
