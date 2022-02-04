package com.saffy.gotcha.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saffy.gotcha.api.request.RoomModifyPostReq;
import com.saffy.gotcha.api.request.RoomRegisterPostReq;
import com.saffy.gotcha.api.service.RoomService;
import com.saffy.gotcha.common.model.response.BaseResponseBody;
import com.saffy.gotcha.entity.Room;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

	@Autowired
	RoomService roomService;

	@PostMapping()
	public ResponseEntity<? extends BaseResponseBody> createRoom(@RequestBody RoomRegisterPostReq roomInfo) {
		roomService.createRoom(roomInfo);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@GetMapping
	public ResponseEntity<List<Room>> getRoomList() {
		return ResponseEntity.status(200).body(roomService.getRooms());
	}

	@GetMapping(value = "/{roomId}")
	public ResponseEntity<Room> getRoom(@PathVariable("roomId") String roomId) {
		return ResponseEntity.status(200).body(roomService.getRoom(roomId));
	}

	@DeleteMapping(value = "/{roomId}")
	public ResponseEntity<String> deleteRoom(@PathVariable("roomId") String roomId) {
		roomService.deleteRoom(roomId);
		return ResponseEntity.status(200).body(null);
	}

	@PatchMapping(value = "/{roomId}")
	public ResponseEntity<? extends BaseResponseBody> modifyRoom(@PathVariable("roomId") String roomId,
			@RequestBody RoomModifyPostReq modifyInfo) {
		Room room = roomService.modifyRoom(roomId, modifyInfo);

		return null;
	}
}
