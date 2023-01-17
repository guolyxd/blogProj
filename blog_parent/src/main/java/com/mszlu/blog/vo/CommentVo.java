package com.mszlu.blog.vo;

import java.util.List;
import lombok.Data;

@Data
public class CommentVo {
    
	private Long id;
    private UserVo author;
    private String content;
    private List<CommentVo> childrens;
    private String createDate;
    private Integer level;
    private UserVo toUser;

}
