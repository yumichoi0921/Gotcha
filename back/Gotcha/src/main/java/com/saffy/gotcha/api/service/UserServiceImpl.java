package com.saffy.gotcha.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saffy.gotcha.api.request.UserRegisterPostReq;
import com.saffy.gotcha.entity.User;
import com.saffy.gotcha.repository.UserRepository;

import lombok.RequiredArgsConstructor;

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
		user.setEmail(userRegisterInfo.getEmail());
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserId(String userId) {
		User user = userRepository.findByUserId(userId);
		return user;
	}

}
