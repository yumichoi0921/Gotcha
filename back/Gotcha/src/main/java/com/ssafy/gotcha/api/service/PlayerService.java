package com.ssafy.gotcha.api.service;

public interface PlayerService {
	public boolean connectPlayer(String connectionId, String roomId, String userId);
	public void disConnectPlayer(String connectionId);
}
