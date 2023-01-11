package com.mszlu.blog.service;

import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginPara;


public interface LoginService {

	Result login(LoginPara loginpara);

}
