package com.mszlu.blog.service;

import com.mszlu.blog.dao.pojo.SysUser;

public interface UserService {
	SysUser findUserById(Long userId);

}
