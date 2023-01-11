package com.mszlu.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mszlu.blog.service.ArticleService;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.PageParams;

@RestController
@RequestMapping("articles")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@PostMapping
	public Result articles(@RequestBody PageParams pageParams)
	   {
		return articleService.listArticle(pageParams);
		}
	
	@PostMapping("hot")
	public Result hotArticles()
	   {
		int limit = 3 ;
		return articleService.hotArticles(limit);
		}
	
	@PostMapping("new")
	public Result newArticles()
	   {
		int limit = 3 ;
		return articleService.newArticles(limit);
		}
	
	@PostMapping("listArchives")
	public Result listArchives()
	   {
		return articleService.listArchives();
		}

}