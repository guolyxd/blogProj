package com.mszlu.blog.service;

import com.mszlu.blog.dao.pojo.SysUser;

public interface SysUserService {

	SysUser findUserById(Long authorId);

	SysUser findUser(String account, String password);

}
