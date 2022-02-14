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
public class PlayerServiceImpl {
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private GameSessionRepository gameSessionRepository;
	@Autowired
	private RoomRepository roomRepository;

	public boolean connectPlayer(String connectionId, String roomId, String userId) {
		// 해당 방에 들어갈 수 있는 지 체크
		Room room = roomRepository.findByRoomId(roomId);
		int participant = room.getParticipant();
		if (room.getCapacity() > participant) { // 현재원이 수용량보다 작다면
			Player player = playerRepository.create(connectionId, roomId, userId);
			gameSessionRepository.findGameSessionById(roomId).getPlayers().put(userId, player);
			room.setParticipant(participant + 1); // 해당 방 인원수 증가
			roomRepository.save(room);
			return true;
		} else {
			// TODO: 입장 불가능 하다는 메세지 전송해줘야하는 부분 -> 여기서 message를 보내줘야되는건지..
			return false;
		}
	}

	public void disConnectPlayer(String connectionId) {
		Player player = playerRepository.get(connectionId);
		String userId = player.getUserId();
		String gameSessionId = player.getRoomId();
		Room room = roomRepository.findByRoomId(gameSessionId); // 해당 커넥션의 방을 찾아서
		
		
		if(room.getParticipant() <= 1) {
			roomRepository.deleteByRoomId(gameSessionId); // 1명이었다면 방 제거
			playerRepository.remove(connectionId);
		} else {
			GameSession gs = gameSessionRepository.findGameSessionById(gameSessionId);
			
			System.out.println(gs.getCardList());
			gs.getPlayers().remove(userId);
			if(gs.getCardList() != null) {
				gs.getCardList().remove(userId);
			}
			playerRepository.remove(connectionId);
			
			room.setParticipant(room.getParticipant()-1); // 수용량을 1 감소 시킴.
			roomRepository.save(room); // 그리고 저장
		}
		
		
	}

}
