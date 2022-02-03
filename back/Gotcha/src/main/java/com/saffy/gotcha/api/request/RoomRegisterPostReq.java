package com.saffy.gotcha.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRegisterPostReq {
	String hostId; // 规 积己夸没茄 user狼 userId (规厘, host)
	String roomTitle;
	String password;
	@JsonProperty("isPrivate")
	boolean isPrivate;
	int capacity;
}
