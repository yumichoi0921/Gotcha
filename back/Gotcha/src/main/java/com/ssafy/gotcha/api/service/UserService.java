package com.ssafy.gotcha.api.service;

import com.ssafy.gotcha.api.request.UserRegisterPostReq;
import com.ssafy.gotcha.entity.User;

public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	
	User getUserByUserId(String userId);
	
	User findByNickNamechek(String nickName);
	
	
}
