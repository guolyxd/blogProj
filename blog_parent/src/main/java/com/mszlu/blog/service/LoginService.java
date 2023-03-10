package com.mszlu.blog.service;

import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginPara;


public interface LoginService {

	Result login(LoginPara loginpara);

	SysUser checkToken(String token);

	Result logout(String token);

	Result register(LoginPara loginPara);

}
