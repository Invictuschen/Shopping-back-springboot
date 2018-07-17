package com.example.shopping.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shopping.bean.User;
import com.example.shopping.bean.UserProfile;
import com.example.shopping.dao.UserDao;
import com.example.shopping.http.Response;

@Service
@Transactional // 多加入transaction的功能 可回滚

public class UserService {
	@Autowired // 利用annotation来创建新的bean对象
	UserDao userDao;

	@Autowired
	PasswordEncoder passwordEncoder;

	public Response register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));// 给密码加密
		List<UserProfile> profiles = new ArrayList<UserProfile>();
		profiles.add(new UserProfile(2));
		user.setProfiles(profiles);
		System.out.println(user);
		userDao.save(user);
		return new Response(true);
	}
}
