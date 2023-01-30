package com.mszlu.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mszlu.blog.service.CategoryService;
import com.mszlu.blog.vo.Result;

@RestController
@RequestMapping("categorys")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public Result listCategories() {
		return categoryService.findAll();
		
	}
	
	@GetMapping("detail")
	public Result listCategoriesDetail() {
		return categoryService.findAllDetail();
		
	}
	
	@GetMapping("detail/{id}")
	public Result listCategoryDetailById(@PathVariable("id") Long id) {
		return categoryService.findAllDetailById(id);
		
	}
	

}
