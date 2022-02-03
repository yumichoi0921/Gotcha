package com.saffy.gotcha.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saffy.gotcha.db.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUserId(String userId);
	public User findByNickName(String nickName);
}
