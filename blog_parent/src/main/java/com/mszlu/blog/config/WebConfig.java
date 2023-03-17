package com.mszlu.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mszlu.blog.handler.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Autowired
	private LoginInterceptor loginInterceptor;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		/*
		 *跨域配置不可以设置为*，这样不安全，前后端分离的项目可能域名不一致
		 *本地测试端口不一致也算跨域 
		 */
		registry.addMapping("/**").allowedOrigins("http://localhost:8080");

	}
	
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor)
		        .addPathPatterns("/test")
		        .addPathPatterns("/comments/create/change")
		        .addPathPatterns("/articles/publish");
	//	        .excludePathPatterns("/register")
	//	        .excludePathPatterns("/login");
	}

}
