package com.mszlu.blog.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.dao.pojo.Article;
import com.mszlu.blog.vo.Archive;

public interface ArticleMapper extends BaseMapper<Article> {
	List<Archive> listArchives();

}

