package com.ssafy.gotcha.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.gotcha.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	@Transactional
	void deleteByRoomId(String roomId);
	//List<User> findByPasswordOrderByRoomId(String Password);
	Room findByRoomId(String roomId);
}
