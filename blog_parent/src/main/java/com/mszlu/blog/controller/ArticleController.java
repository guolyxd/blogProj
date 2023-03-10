package com.mszlu.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mszlu.blog.common.aop.LogAnnotation;
import com.mszlu.blog.common.cache.Cache;
import com.mszlu.blog.service.ArticleService;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.ArticlePara;
import com.mszlu.blog.vo.params.PageParams;

@RestController
@RequestMapping("articles")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@PostMapping
	@LogAnnotation(module="Article",operator="Get article list")
	@Cache(expire = 5*60*1000, name = "list_Article")
	public Result articles(@RequestBody PageParams pageParams)
	   {
		return articleService.listArticle(pageParams);
		}

	@PostMapping("hot")
	@Cache(expire = 5*60*1000, name = "hot_article")
	public Result hotArticles()
	   {
		int limit = 3 ;
		return articleService.hotArticles(limit);
		}

	@PostMapping("new")
	@Cache(expire = 5*60*1000, name = "new_article")
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
	
	@PostMapping("view/{id}")
	public Result findArticleById(@PathVariable("id") Long articleId) {
		return articleService.findArticleById(articleId);
		
	}
	
	@PostMapping("publish")
	public Result publish(@RequestBody ArticlePara articlePara) {
		return articleService.publish(articlePara);
	}
	
	@PostMapping("{id}")
	public Result amendArticleById(@PathVariable("id") Long articleId) {
		return articleService.findArticleById(articleId);
		
	}

}
