package com.zhouq.eshop.inventory.controller;

import com.zhouq.eshop.inventory.model.User;
import com.zhouq.eshop.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户Controller控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	@ResponseBody
	public User getUserInfo(@PathVariable("id") int id) {
		User user = userService.findUserById(id);
		return user;
	}
	
	@RequestMapping("/getCachedUserInfo")
	@ResponseBody
	public User getCachedUserInfo() {
//		User user = userService.getCachedUserInfo();
		return null;
	}
	
}
