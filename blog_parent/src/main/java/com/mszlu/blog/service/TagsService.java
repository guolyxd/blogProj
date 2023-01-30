package com.mszlu.blog.service;

import java.util.List;

import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.TagVo;

public interface TagsService {
	List<TagVo> findTagsByArticleId(Long id);

	Result hots(int limit);

	Result findAll();

	Result findAllDetail();

	Result findDetailById(Long id);



}
