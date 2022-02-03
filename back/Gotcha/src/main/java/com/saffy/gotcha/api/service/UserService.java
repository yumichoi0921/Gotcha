package com.saffy.gotcha.api.service;

import com.saffy.gotcha.api.request.UserRegisterPostReq;
import com.saffy.gotcha.entity.User;

public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	
	User getUserByUserId(String userId);
	
}
