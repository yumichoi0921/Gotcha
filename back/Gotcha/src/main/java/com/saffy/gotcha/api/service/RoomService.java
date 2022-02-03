package com.saffy.gotcha.api.service;

import java.util.List;

import com.saffy.gotcha.api.request.RoomRegisterPostReq;
import com.saffy.gotcha.entity.Room;

public interface RoomService {
	Room createRoom(RoomRegisterPostReq roomRegisterPostReq);
	List<Room> getRooms();
	
}
