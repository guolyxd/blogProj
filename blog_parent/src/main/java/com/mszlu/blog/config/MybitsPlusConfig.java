package com.mszlu.blog.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

@Configuration
//添加MyBatisPlus到注册表里面
@MapperScan("com.mszlu.blog.dao.mapper")
public class MybitsPlusConfig {

      //分页插件， 在blog home page , 当下拉文章列表时需要加载新的文章内容，这时分页插件就起作用了
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		return interceptor;
	}

}