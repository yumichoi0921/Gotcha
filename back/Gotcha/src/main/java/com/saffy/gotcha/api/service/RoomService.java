package com.saffy.gotcha.api.service;

import java.util.List;

import com.saffy.gotcha.api.request.RoomModifyPostReq;
import com.saffy.gotcha.api.request.RoomRegisterPostReq;
import com.saffy.gotcha.entity.Room;

public interface RoomService {
	Room createRoom(RoomRegisterPostReq roomRegisterPostReq);
	List<Room> getRooms();
	Room getRoom(String roomId);
	void deleteRoom(String roomId);
	Room modifyRoom(String roomId, RoomModifyPostReq modifyInfo);
}
