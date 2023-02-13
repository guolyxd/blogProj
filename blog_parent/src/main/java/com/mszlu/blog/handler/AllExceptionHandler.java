package com.mszlu.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mszlu.blog.vo.Result;

/*对所有加了controller注解的类进行异常拦截处理，也属于AOP处理*/
@ControllerAdvice
public class AllExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result doException(Exception ex) {
		ex.printStackTrace();
		return Result.fail(-999,"系统异常");
	}
}