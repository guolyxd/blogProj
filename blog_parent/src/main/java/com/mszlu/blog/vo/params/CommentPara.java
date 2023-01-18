package com.mszlu.blog.vo.params;

import lombok.Data;

@Data
public class CommentPara {
	
	private Long articleId;
	private Long parent;
	private Long toUserId;
	private String content;

}
