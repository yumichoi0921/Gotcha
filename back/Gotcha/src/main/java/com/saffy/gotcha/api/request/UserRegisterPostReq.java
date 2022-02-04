package com.saffy.gotcha.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@ApiModel(value = "UserRegisterPostReq : 회워가입", description = "회원가입 정보의 상세 정보를 나타낸다.")
public class UserRegisterPostReq {
	@ApiModelProperty(value = "유저 아이디")
	String userId;
	@ApiModelProperty(value = "유저 닉네임")
	String nickName;
	@ApiModelProperty(value = "유저 이메일")
	String email;
	@ApiModelProperty(value = "유저 패스워드")
	String password;
}
