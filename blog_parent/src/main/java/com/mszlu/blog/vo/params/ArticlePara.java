package com.mszlu.blog.vo.params;

import java.util.List;

import com.mszlu.blog.vo.CategoryVo;
import com.mszlu.blog.vo.TagVo;

import lombok.Data;

@Data
public class ArticlePara {
    
	private Long id;
    private ArticleBodyParam body;
    private CategoryVo category;
    private String summary;
    private List<TagVo> tags;
    private String title;
    
}
