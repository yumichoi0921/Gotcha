package com.ssafy.gotcha.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.gotcha.api.request.RoomModifyPostReq;
import com.ssafy.gotcha.api.request.RoomRegisterPostReq;
import com.ssafy.gotcha.entity.Room;
import com.ssafy.gotcha.entity.User;
import com.ssafy.gotcha.repository.GameSessionRepository;
import com.ssafy.gotcha.repository.RoomRepository;
import com.ssafy.gotcha.repository.UserRepository;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private UserRepository userRopository;

	@Autowired
	private GameSessionRepository gameSessionRepository;

	@Override
	public Room createRoom(RoomRegisterPostReq roomRegisterPostReq) {
		User host = userRopository.findByUserId(roomRegisterPostReq.getHostId());

		String gameSessionId = UUID.randomUUID().toString();

		Room room = Room.builder().roomId(gameSessionId).password(roomRegisterPostReq.getPassword())
				.roomTitle(roomRegisterPostReq.getRoomTitle()).createdAt(LocalDateTime.now())
				.isPrivate(roomRegisterPostReq.isPrivate()).capacity(roomRegisterPostReq.getCapacity()).participant(0)
				.isFull(false).isRun(false).build();

		gameSessionRepository.createGameSession(gameSessionId, host.getUserId());
		return roomRepository.save(room);
	}

	@Override
	public List<Room> getRooms() {
		return roomRepository.findAll();
	}

	@Override
	public Room getRoom(String roomId) {
		return roomRepository.findByRoomId(roomId);
	}

	@Override
	public void deleteRoom(String roomId) {
		roomRepository.deleteByRoomId(roomId);
	}

	@Override
	public Room modifyRoom(String roomId, RoomModifyPostReq modifyInfo) {
		Room room = roomRepository.findByRoomId(roomId);
		room.setParticipant(modifyInfo.getParticipant());
		room.setFull(modifyInfo.isFull());
		room.setRun(modifyInfo.isRun());
		return roomRepository.save(room);
	}

	@Override
	public Room changeIsFull(String roomId, boolean isFull) {
		Room room = roomRepository.findByRoomId(roomId);
		room.setFull(isFull);
		return roomRepository.save(room);
	}

	@Override
	public Room changeIsRun(String roomId, boolean isRun) {
		Room room = roomRepository.findByRoomId(roomId);
		room.setRun(isRun);
		return roomRepository.save(room);
	}
}
