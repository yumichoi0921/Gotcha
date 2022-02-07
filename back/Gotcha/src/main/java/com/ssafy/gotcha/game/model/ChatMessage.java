package com.ssafy.gotcha.game.model;

import lombok.Getter;
import lombok.Setter;

// 클라이언트와 서버 간에 교환되는 메시지 페이로드로 사용될 모델 클래스
@Getter
@Setter
public class ChatMessage {

	private String roomId;
	private MessageType type;
    private String content;
    private String sender;
}