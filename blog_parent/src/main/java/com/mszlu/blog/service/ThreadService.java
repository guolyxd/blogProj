package com.mszlu.blog.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mszlu.blog.dao.mapper.ArticleMapper;
import com.mszlu.blog.dao.pojo.Article;

@Component
public class ThreadService {

	@Async("taskExecutor")
	public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
		
		int viewCounts =article.getViewCounts();
		Article articleUpdate = new Article();
		articleUpdate.setViewCounts(viewCounts + 1);
		LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(Article::getId,article.getId());
		updateWrapper.eq(Article::getViewCounts, viewCounts);
		articleMapper.update(articleUpdate, updateWrapper);
		
		try {
			Thread.sleep(5000);
			System.out.println("update complete");		
		}
		catch(InterruptedException e) {
			e.printStackTrace();;
		}
		
	}

}
