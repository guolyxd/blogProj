package com.mszlu.blog.service;

import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.PageParams;

public interface ArticleService {

	 /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */

	Result listArticle (PageParams pageParams);
	Result hotArticles(int limit);
	Result newArticles(int limit);
	Result listArchives();
	Result findArticleById(Long articleId);

}
