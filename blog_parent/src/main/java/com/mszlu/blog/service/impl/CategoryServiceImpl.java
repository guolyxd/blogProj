package com.mszlu.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mszlu.blog.dao.mapper.CategoryMapper;
import com.mszlu.blog.dao.pojo.Category;
import com.mszlu.blog.service.CategoryService;
import com.mszlu.blog.vo.CategoryVo;


@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired 
	private CategoryMapper categoryMapper;

	@Override
	public CategoryVo findCategoryById(Long categoryId) {
		Category category = categoryMapper.selectById(categoryId);
		CategoryVo categoryVo = new CategoryVo();
		BeanUtils.copyProperties(category, categoryVo);
		
		return categoryVo;
	}

}
