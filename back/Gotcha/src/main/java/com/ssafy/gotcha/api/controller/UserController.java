package com.ssafy.gotcha.api.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.gotcha.api.jwt.JwtFilter;
import com.ssafy.gotcha.api.jwt.TokenProvider;
import com.ssafy.gotcha.api.request.UserLoginPostReq;
import com.ssafy.gotcha.api.request.UserRegisterPostReq;
import com.ssafy.gotcha.api.response.BaseResponseBody;
import com.ssafy.gotcha.api.service.UserService;
import com.ssafy.gotcha.vo.Token;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@Api(value = "User 컨트롤러 API")
@RequiredArgsConstructor
public class UserController {

	@Autowired
	UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    
    
	@ApiOperation(value = "registerInfo", notes = "회원가입")
	@PostMapping("/signup")
	public ResponseEntity<? extends BaseResponseBody> register(@RequestBody UserRegisterPostReq registerInfo) {
		userService.createUser(registerInfo);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
	
	@ApiOperation(value = "로그인", response = Token.class)
    @PostMapping("/login")
    public ResponseEntity<Token> login(@Valid @ApiParam(value = "id와 password", required = true) @RequestBody UserLoginPostReq userLogin) throws Exception{
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLogin.getUserId(), userLogin.getPassword());
        //유저 정보를 조회하여 인증 정보를 생성
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        //해당 인증 정보를 현재 실행중인 스레드(Security Context)에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //해당 인증 정보를 기반으로 jwt 토큰을 생성
        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        //생성된 Token을 Response Header에 넣고, Token vo 객체를 이용해 Response Body에도 넣어서 리턴
        return new ResponseEntity<>(new Token(jwt), httpHeaders, HttpStatus.OK);
    }
	
	@ApiOperation(value = "아이디 중복 체크")
	@GetMapping("/userIdCheck")
	public ResponseEntity<Boolean> userIdCheck(String userId) {
		if (userService.getUserByUserId(userId) != null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "닉네임 중복 체크")
	@GetMapping("/nickNameCheck")
	public ResponseEntity<Boolean> nickNameCheck(String nickName) {
		if (userService.findByNickNamecheck(nickName) != null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	

}
