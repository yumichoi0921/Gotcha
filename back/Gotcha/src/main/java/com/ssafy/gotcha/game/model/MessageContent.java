package com.ssafy.gotcha.game.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageContent {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public String toContent() throws JsonProcessingException {
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
	}
}
