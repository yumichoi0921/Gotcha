package com.saffy.gotcha.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;

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
public class Room extends BaseEntity {
	String roomId; // sessionId
	String password;
	String roomTitle;
	LocalDateTime createdAt;
	int capacity;
	int participant;
	boolean isFull;
	boolean isPrivate;
	boolean isRun;
}
