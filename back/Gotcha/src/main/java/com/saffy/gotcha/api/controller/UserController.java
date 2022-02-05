package com.saffy.gotcha.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saffy.gotcha.api.request.UserRegisterPostReq;
import com.saffy.gotcha.api.response.BaseResponseBody;
import com.saffy.gotcha.api.service.UserService;
import com.saffy.gotcha.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/users")
@Api(value = "User 컨트롤러 API")
public class UserController {

	@Autowired
	UserService userService;
	
	@ApiOperation(value = "registerInfo", notes = "로그인")
	@PostMapping("/signup")
	public ResponseEntity<? extends BaseResponseBody> register(@RequestBody UserRegisterPostReq registerInfo) {
		User user = userService.createUser(registerInfo);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

}
