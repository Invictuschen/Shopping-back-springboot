package com.example.shopping.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shopping.bean.User;

public interface UserDao extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
