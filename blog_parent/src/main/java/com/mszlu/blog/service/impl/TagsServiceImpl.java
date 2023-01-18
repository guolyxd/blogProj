package com.mszlu.blog.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.dao.mapper.TagMapper;
import com.mszlu.blog.dao.pojo.Tag;
import com.mszlu.blog.service.TagsService;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.TagVo;

@Service
public class TagsServiceImpl implements TagsService {
    @Autowired
    private TagMapper tagMapper;

    /**
     * ms_article_tag是文章和标签的关联表
     * ms_tag为单纯的标签的表
     * @param articleId
     * @return
     */

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //mybatisplus无法进行多表查询
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    private List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;

    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }

    @Override
	public Result hots(int limit) {

    	List<Long> tagIds = tagMapper.findHotsTagIds(limit);
    	if(CollectionUtils.isEmpty(tagIds)) {
    		return Result.success(Collections.emptyList());
    	}

    	List<Long> tagLists = tagMapper.findTagsByTagIds(tagIds);
    	    return Result.success(tagLists);
    }

    
	@Override
	public Result findAll() {
		List<Tag> tags = this.tagMapper.selectList(new LambdaQueryWrapper<>());
		return Result.success(copyList(tags));
	}
}

