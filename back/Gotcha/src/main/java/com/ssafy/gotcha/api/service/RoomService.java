package com.ssafy.gotcha.api.service;

import java.util.List;

import com.ssafy.gotcha.api.request.RoomModifyPostReq;
import com.ssafy.gotcha.api.request.RoomRegisterPostReq;
import com.ssafy.gotcha.entity.Room;

public interface RoomService {
	Room createRoom(RoomRegisterPostReq roomRegisterPostReq);
	List<Room> getRooms();
	Room getRoom(String roomId);
	void deleteRoom(String roomId);
	Room modifyRoom(String roomId, RoomModifyPostReq modifyInfo);
}
