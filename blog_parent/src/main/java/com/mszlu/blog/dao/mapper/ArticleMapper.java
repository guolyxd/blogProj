package com.mszlu.blog.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.blog.dao.pojo.Article;
import com.mszlu.blog.vo.Archive;

public interface ArticleMapper extends BaseMapper<Article> {
	List<Archive> listArchives();
	
	IPage<Article> listArticle(Page<Article> page,
			                   Long categoryId,
			                   Long tagId,
			                   String month,
			                   String year);

}

