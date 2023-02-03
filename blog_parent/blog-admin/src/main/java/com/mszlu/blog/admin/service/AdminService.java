package com.mszlu.blog.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.admin.mapper.AdminMapper;
import com.mszlu.blog.admin.pojo.Admin;

@Service
public class AdminService {
	
	@Autowired
	private AdminMapper adminMapper;
	
	public Admin findUserByUsername(String username) {
		LambdaQueryWrapper<Admin> queryWrapper =new LambdaQueryWrapper<>();
		queryWrapper.eq(Admin::getUsername, username);
		queryWrapper.last("limit 1");
		Admin admin = adminMapper.selectOne(queryWrapper);
		
		return admin;
	}

}
