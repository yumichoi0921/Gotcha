package com.ssafy.gotcha.game.model;

import com.google.gson.Gson;

public class MessageContent {
	
	private static final Gson gson = new Gson();
	
//	private static final ObjectMapper mapper = new ObjectMapper();
//	public String toContent() throws JsonProcessingException {
//		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
//	}
	
	public String toContent() {
		return gson.toJson(this);
	}
}
