package com.ssafy.gotcha.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.gotcha.api.request.UserRegisterPostReq;
import com.ssafy.gotcha.entity.User;
import com.ssafy.gotcha.repository.UserRepository;
import com.ssafy.gotcha.vo.Role;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		System.out.println("service");
		User user = new User();

		user.setUserId(userRegisterInfo.getUserId());
		user.setNickName(userRegisterInfo.getNickName());
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		user.setRole(Role.USER);
		user.setEmail(userRegisterInfo.getEmail());
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserId(String userId) {
		try {
		User user = userRepository.findByUserId(userId);
		return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User findByNickNamecheck(String nickName) {
		try {
			User user = userRepository.findByNickName(nickName);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	

}
