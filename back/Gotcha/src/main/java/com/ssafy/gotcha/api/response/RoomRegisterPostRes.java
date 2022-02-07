package com.ssafy.gotcha.api.response;

import com.ssafy.gotcha.entity.Room;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class RoomRegisterPostRes {
	String roomId; // 생성된 roomId(SessionId와 동일)
	
	public static RoomRegisterPostRes of(Room room) {
		RoomRegisterPostRes res = new RoomRegisterPostRes();
		res.setRoomId(room.getRoomId());
		return res;
	}
}
