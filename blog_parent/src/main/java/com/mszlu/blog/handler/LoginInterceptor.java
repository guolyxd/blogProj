package com.mszlu.blog.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSON;
import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.service.LoginService;
import com.mszlu.blog.utils.UserThreadLocal;
import com.mszlu.blog.vo.ErrorCode;
import com.mszlu.blog.vo.Result;

import lombok.extern.slf4j.Slf4j;

/*登录拦截器*/

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor{
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		
		if(!(handler instanceof HandlerMethod)) {
			return true;
		}
		
		String token = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

		
		if(StringUtils.isBlank(token)) {
			Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(),"未登录");
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(JSON.toJSONString(result));
			return false;
		}
		
		SysUser sysUser = loginService.checkToken(token);
		if(sysUser==null) {
			Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(),"未登录");
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(JSON.toJSONString(result));
			return false;
		}
		
		UserThreadLocal.put(sysUser);
		
		return true;
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
		//如果不删除 ThreadLocal中用完的信息 会有内存泄漏的风险
		UserThreadLocal.remove();
	};

}
