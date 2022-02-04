package com.saffy.gotcha.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RoomRegisterPostReq : 방 정보", description = "방 정보의 상세 정보를 나타낸다.")
public class Room extends BaseEntity {
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
}
