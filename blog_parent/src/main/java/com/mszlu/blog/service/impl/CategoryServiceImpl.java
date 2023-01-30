package com.mszlu.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.dao.mapper.CategoryMapper;
import com.mszlu.blog.dao.pojo.Category;
import com.mszlu.blog.service.CategoryService;
import com.mszlu.blog.vo.CategoryVo;
import com.mszlu.blog.vo.Result;


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

	@Override
	public Result findAll() {
		
		LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.select(Category::getId,Category::getCategoryName);
		List<Category> categories = categoryMapper.selectList(queryWrapper);
		
		return Result.success(copyList(categories));
	}
	
	public List<CategoryVo> copyList(List<Category> categoryList) {
		List<CategoryVo> categoryVoList = new ArrayList<>();
		for (Category category : categoryList) {
			categoryVoList.add(copy(category));
		}
		
		return categoryVoList;
	}
	
	public CategoryVo copy(Category category) {
		CategoryVo categoryVo = new CategoryVo();
		BeanUtils.copyProperties(category, categoryVo);
		
		return categoryVo;
	}

	@Override
	public Result findAllDetail() {
		LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
		List<Category> categories = categoryMapper.selectList(queryWrapper);
		
		return Result.success(copyList(categories));
	}

	@Override
	public Result findAllDetailById(Long id) {
		Category category = categoryMapper.selectById(id);
		return Result.success(copy(category));
	}

	

}
