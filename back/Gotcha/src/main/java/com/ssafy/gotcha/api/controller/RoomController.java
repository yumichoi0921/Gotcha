package com.ssafy.gotcha.api.controller;

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

import com.ssafy.gotcha.api.request.RoomModifyPostReq;
import com.ssafy.gotcha.api.request.RoomRegisterPostReq;
import com.ssafy.gotcha.api.response.BaseResponseBody;
import com.ssafy.gotcha.api.response.RoomGetRes;
import com.ssafy.gotcha.api.response.RoomRegisterPostRes;
import com.ssafy.gotcha.api.service.RoomService;
import com.ssafy.gotcha.entity.Room;
import com.ssafy.gotcha.repository.GameSessionRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "room 컨트롤러 API")
@RequestMapping("/api/rooms")
public class RoomController {

	@Autowired
	RoomService roomService;
	@Autowired
	GameSessionRepository gameSessionRepository;

	@ApiOperation(value = "roomInfo", notes = "새로운 room 만들기")
	@PostMapping()
	public ResponseEntity<RoomRegisterPostRes> createRoom(@RequestBody RoomRegisterPostReq roomInfo) {
		Room room = roomService.createRoom(roomInfo);
		return ResponseEntity.status(200).body(RoomRegisterPostRes.of(room));
	}

	@ApiOperation(value = "", notes = "생성된 모든 room의 정보를 반환한다.")
	@GetMapping
	public ResponseEntity<List<Room>> getRoomList() {
		return ResponseEntity.status(200).body(roomService.getRooms());
	}

	@GetMapping(value = "/{roomId}")
	public ResponseEntity<RoomGetRes> getRoom(@PathVariable("roomId") String roomId) {
		Room room = roomService.getRoom(roomId);
		return ResponseEntity.status(200)
				.body(RoomGetRes.of(room, gameSessionRepository.findGameSessionById(room.getRoomId()).getHostId()));
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

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
}
