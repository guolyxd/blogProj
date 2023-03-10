package com.mszlu.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mszlu.blog.service.LoginService;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginPara;

@RestController
@RequestMapping("register")
public class RegisterController {
	
	@Autowired
	private LoginService loginService;
	
	@PostMapping
	public Result register(@RequestBody LoginPara loginPara) {
		
		return loginService.register(loginPara);
		
	}

}
