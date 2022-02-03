package com.saffy.gotcha.entity;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.saffy.gotcha.game.model.Player;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends BaseEntity {
	
	String userId;
	String nickName;
	String email;
	
	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	String password;
	
	public Player toPlayer() {
		return Player.builder() // »ý¼ºÀÚ
				.userId(userId)
				.nickName(nickName)
				.readyState(false)
				.isActivated(true)
				.build();
	}
}
