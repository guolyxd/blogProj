package com.mszlu.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mszlu.blog.service.CommentsService;
import com.mszlu.blog.vo.Result;

@RestController
@RequestMapping("comments")
public class CommentController {
	
	@Autowired
	private CommentsService commentsService;
	
	@GetMapping("article/{id}")
	public Result Comments(@PathVariable("id") Long id) {
		return commentsService.commentsArticleById(id);
		
	}

}
