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
		registry.addMapping("/**").allowedOrigins("http://localhost:8080");

	}
	
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor)
		        .addPathPatterns("/**")
		        .excludePathPatterns("/register")
		        .excludePathPatterns("/login");
	}

}
