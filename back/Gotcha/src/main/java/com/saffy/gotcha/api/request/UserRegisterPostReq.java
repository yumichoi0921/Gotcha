package com.saffy.gotcha.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterPostReq {
	String userId;
	String nickName;
	String email;
	String password;
}
