package com.saffy.gotcha.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saffy.gotcha.api.request.UserRegisterPostReq;
import com.saffy.gotcha.api.service.UserService;
import com.saffy.gotcha.common.model.response.BaseResponseBody;
import com.saffy.gotcha.db.entity.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<? extends BaseResponseBody> register(@RequestBody UserRegisterPostReq registerInfo) {
		System.out.println("controller");
		User user = userService.createUser(registerInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
	
}
