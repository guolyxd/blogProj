package com.mszlu.blog.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.dao.pojo.Tag;

public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> findTagsByArticleId(Long articleId);

	List<Long> findHotsTagIds(int limit);

	List<Long> findTagsByTagIds(List<Long> tagIds);
}

