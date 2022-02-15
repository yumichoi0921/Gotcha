package com.ssafy.gotcha.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.gotcha.entity.Room;
import com.ssafy.gotcha.game.model.GameSession;
import com.ssafy.gotcha.game.model.Player;
import com.ssafy.gotcha.repository.GameSessionRepository;
import com.ssafy.gotcha.repository.PlayerRepository;
import com.ssafy.gotcha.repository.RoomRepository;

@Service
public class PlayerServiceImpl implements PlayerService {
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private GameSessionRepository gameSessionRepository;
	@Autowired
	private RoomRepository roomRepository;

	@Override
	public boolean connectPlayer(String connectionId, String roomId, String userId) {
		// 해당 방에 들어갈 수 있는 지 체크
		Room room = roomRepository.findByRoomId(roomId);
		int participant = room.getParticipant();
		int capacity = room.getCapacity();
		if (capacity > participant) { // 현재원이 수용량보다 작다면
			Player player = playerRepository.create(connectionId, roomId, userId);
			gameSessionRepository.findGameSessionById(roomId).getPlayers().put(userId, player);
			participant += 1; // 해당 방 인원수 증가
			if (capacity == participant) { // 다 차면
				room.setFull(true);
			}
			room.setParticipant(participant); 
			roomRepository.save(room);
			return true;
		} else {
			System.out.println("인원수 초과! 입장 불가");
			// TODO: 입장 불가능 하다는 메세지 전송해줘야하는 부분 -> 여기서 message를 보내줘야되는건지..
			return false;
		}
	}

	@Override
	public void disConnectPlayer(String connectionId) {
		System.out.println("1");
		Player player = playerRepository.get(connectionId);
		String userId = player.getUserId();
		String gameSessionId = player.getRoomId();
		Room room = roomRepository.findByRoomId(gameSessionId); // 해당 커넥션의 방을 찾아서
		int capacity = room.getCapacity();
		int participant = room.getParticipant();
		if (participant <= 1) {
			System.out.println("2");
			gameSessionRepository.removeGameSession(gameSessionId); // game session 제거
			System.out.println("2-1");
			roomRepository.deleteByRoomId(gameSessionId); // 1명이었다면 방 제거
			System.out.println("2-2");
			playerRepository.remove(connectionId);
			System.out.println("2-3");
		} else {
			System.out.println("3");
			GameSession gs = gameSessionRepository.findGameSessionById(gameSessionId);
			gs.getPlayers().remove(userId);
			if (gs.getCardList() != null) {
				System.out.println("4");
				gs.getCardList().remove(userId);
			}
			playerRepository.remove(connectionId);
			participant -= 1; // 해당 방 인원수 감소
			if (capacity > participant) { // 다 안찼다면
				System.out.println("5");
				room.setFull(false);
			}
			System.out.println("6");

			room.setParticipant(participant); // 수용량을 1 감소 시킴.
			System.out.println("7");
			roomRepository.save(room); // 그리고 저장
			System.out.println("8");

		}
		System.out.println("빠밤");
	}

}
