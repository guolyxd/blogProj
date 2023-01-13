package com.mszlu.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mszlu.blog.vo.Result;

@RestController
@RequestMapping("test")
public class TestController {
	
	@RequestMapping
	public Result test() {
		
		return Result.success(null);
	}

}
