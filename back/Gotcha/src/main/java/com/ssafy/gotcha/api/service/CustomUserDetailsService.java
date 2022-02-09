package com.ssafy.gotcha.api.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.gotcha.entity.User;
import com.ssafy.gotcha.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        User user = userRepository.findByUserId(username);

        
        return org.springframework.security.core.userdetails.User.builder()
        		.username(user.getUserId())
                .password(user.getPassword())
                .roles(user.getRole().getKey())
                .build();
    }
}
