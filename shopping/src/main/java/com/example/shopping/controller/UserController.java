package com.example.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.bean.User;
import com.example.shopping.http.Response;
import com.example.shopping.service.UserService;

@RestController()
@RequestMapping("/users") // controller是对后台进restfulapi

public class UserController {
	@Autowired
	UserService userService;

	@PostMapping
	public Response addUser(@RequestBody User user) { // @RequestBody
														// 把user最后写成json
														// object格式
		return userService.register(user);
	}
}
