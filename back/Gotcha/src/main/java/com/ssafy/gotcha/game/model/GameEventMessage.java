package com.ssafy.gotcha.game.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameEventMessage extends MessageContent {
	private String chooser;
	private String chosen;
	private int selectedCardIdx;
}
