package com.mszlu.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mszlu.blog.service.LoginService;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginPara;

@RestController
@RequestMapping("logout")
public class LogoutController {

	@Autowired
	private LoginService loginService;

	@GetMapping
	public Result logout(@RequestHeader("Authorization") String token) {

		return loginService.logout(token);
	}


}
