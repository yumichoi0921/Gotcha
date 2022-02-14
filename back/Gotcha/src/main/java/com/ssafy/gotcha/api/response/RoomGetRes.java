package com.ssafy.gotcha.api.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.gotcha.entity.Room;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomGetRes {
	@ApiModelProperty(value = "방 아이디(세션 아이디)")
	String roomId; // sessionId
	@ApiModelProperty(value = "방 비밀번호")
	String password;
	@ApiModelProperty(value = "방 제목")
	String roomTitle;
	@ApiModelProperty(value = "방 생성 시간")
	LocalDateTime createdAt;
	@ApiModelProperty(value = "최대 인원 수")
	int capacity;
	@ApiModelProperty(value = "참가 인원 수 ")
	int participant;
	@ApiModelProperty(value = "인원 초과 여부")
	@JsonProperty("isFull")
	boolean isFull;
	@ApiModelProperty(value = "방 공개 여부")
	@JsonProperty("isPrivate")
	boolean isPrivate;
	@ApiModelProperty(value = "게임 실행 여부")
	@JsonProperty("isRun")
	boolean isRun;
	@ApiModelProperty(value = "hostID")
	String hostId;
	
	public static RoomGetRes of(Room room, String hostId) {
		RoomGetRes res = room.toRoomGetRes();
		res.setHostId(hostId);
		return res;
	}
}
