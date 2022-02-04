package com.saffy.gotcha.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saffy.gotcha.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	@Transactional
	void deleteByRoomId(String roomId);
	Room findByRoomId(String roomId);
}
