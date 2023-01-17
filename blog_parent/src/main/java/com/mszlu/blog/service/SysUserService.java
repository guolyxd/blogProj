package com.mszlu.blog.service;

import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.UserVo;

public interface SysUserService {
	
	UserVo findUserVoById(Long id);
	SysUser findUserById(Long authorId);
	SysUser findUser(String account, String password);
	Result findUserByToken(String token);
	SysUser findUserByAccount(String account);
	void save(SysUser sysUser);

}
