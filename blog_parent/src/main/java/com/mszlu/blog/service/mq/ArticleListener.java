package com.mszlu.blog.service.mq;

import java.time.Duration;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.mszlu.blog.service.ArticleService;
import com.mszlu.blog.vo.ArticleMessage;
import com.mszlu.blog.vo.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RocketMQMessageListener(topic="blog-update-article",consumerGroup="blog-update-article-group")
public class ArticleListener implements RocketMQListener<ArticleMessage>{

	@Autowired
	private ArticleService articleService;
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	
	@Override
	public void onMessage(ArticleMessage message) {
		log.info("Received message:{}",message);
		Long articleId = message.getArticleId();
		String para = DigestUtils.md5Hex(articleId.toString());
		String redisKey = "view_article::ArticleController::findArticleById::" +para;
		Result articleResult = articleService.findArticleById(articleId);
		redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(articleResult),Duration.ofMillis(5*60*1000));
		log.info("Updated cache:{}",redisKey);
		
		Set<String> keys = redisTemplate.keys("list_Article*");
		keys.forEach(s->{
			redisTemplate.delete(s);
			log.info("Delete article list cache:{}",s);
		});
		
	}

}
