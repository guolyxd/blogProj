package com.mszlu.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mszlu.blog.service.TagsService;
import com.mszlu.blog.vo.Result;

@RestController
@RequestMapping("tags")
public class TagsController {

	@Autowired
	private TagsService tagsService;


	@GetMapping("hot")
	public Result hot() {
		int limit = 6;
		return tagsService.hots(limit);

	}
	
	@GetMapping
	public Result tags() {
		return tagsService.findAll();

	}

}
