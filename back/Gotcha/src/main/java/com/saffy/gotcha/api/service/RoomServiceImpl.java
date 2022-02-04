package com.saffy.gotcha.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saffy.gotcha.api.request.RoomModifyPostReq;
import com.saffy.gotcha.api.request.RoomRegisterPostReq;
import com.saffy.gotcha.entity.Room;
import com.saffy.gotcha.entity.User;
import com.saffy.gotcha.repository.GameSessionRepository;
import com.saffy.gotcha.repository.RoomRepository;
import com.saffy.gotcha.repository.UserRepository;

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
		
		Room room = Room.builder()
				  .roomId(gameSessionId) 
				  .password(roomRegisterPostReq.getPassword())
						.roomTitle(roomRegisterPostReq.getRoomTitle())
						.createdAt(LocalDateTime.now())
						.isPrivate(roomRegisterPostReq.isPrivate())
						.capacity(roomRegisterPostReq.getCapacity())
						.participant(1)
						.isFull(false)
						.isRun(false)
						.build();
		
		gameSessionRepository.createGameSession(gameSessionId, host.toPlayer());
		
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
}
