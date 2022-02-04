package com.saffy.gotcha.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "RoomRegisterPostReq : 방 생성", description = "방 생성의 상세 정보를 나타낸다.")
public class RoomRegisterPostReq {
	@ApiModelProperty(value = "호스트 아이디")
	String hostId; // 방 생성을 요청한 ID (방장, host)
	@ApiModelProperty(value = "room 이름")
	String roomTitle;
	@ApiModelProperty(value = "room 비밀번호")
	String password;
	@ApiModelProperty(value = "방 공개 여부")
	@JsonProperty("isPrivate")
	boolean isPrivate;
	@ApiModelProperty(value = "인원수")
	int capacity;
}
