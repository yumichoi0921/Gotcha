package com.ssafy.gotcha.game.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameEventMessage extends MessageContent {
	private String eventType;
	private String picker;
	private String picked;
	private int selectedCard;
}
