package com.ssafy.gotcha.game.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Player { 
	
	/*
	*게임방에서 필요한 플레이어에 대한 정보.
	*User Entity의 일부 정보 포함.
	**/
	
	private String userId; // User Entity의 아이디 -> 친구추가기능에서 쓰일수있으니까?
	private String nickName; // User Entity의 닉네임 -> 창에 보여줘야돼
	private boolean readyState; // 레디상태 체크
	private boolean isActivated; // 활성화 여부(true: 게임 진행 가능 / false: 게임 진행 불가 상태)
	private String connectionId; // openVidu에 접속하면 개인별로 할당되는 connectionId
	// boolean isLeft; // 방 나갔는지 여부
}
