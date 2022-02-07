package com.ssafy.gotcha.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomModifyPostReq {
	int participant; // 현재원
	@JsonProperty("isFull")
	boolean isFull; // 풀방인지
	@JsonProperty("isRun")
	boolean isRun; //게임중인지
}
