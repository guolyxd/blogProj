package com.mszlu.blog.admin.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.blog.admin.mapper.PermissionMapper;
import com.mszlu.blog.admin.model.params.PageParam;
import com.mszlu.blog.admin.pojo.Permission;
import com.mszlu.blog.admin.vo.PageResult;
import com.mszlu.blog.admin.vo.Result;

@Service
public class PermissionService {
	
	@Autowired
	private PermissionMapper permissionMapper;
	

	public Result listPermission(PageParam pageParam) {
		Page<Permission> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
		LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
		
		if(StringUtils.isNoneBlank(pageParam.getQueryString())) {
			queryWrapper.eq(Permission::getName, pageParam.getQueryString());
		}
		
		Page<Permission> permissionPage =permissionMapper.selectPage(page, queryWrapper);
		PageResult<Permission> pageResult = new PageResult<>();
		pageResult.setList(permissionPage.getRecords());
		pageResult.setTotal(permissionPage.getTotal());
		
		return Result.success(pageResult);
	}


	public Result add(Permission permission) {
		
		this.permissionMapper.insert(permission);
		return Result.success(null);
	}


	public Result update(Permission permission) {
		this.permissionMapper.updateById(permission);
		return Result.success(null);
	}


	public Result delete(Long id) {
		this.permissionMapper.deleteById(id);
		return Result.success(null);
	}

}
