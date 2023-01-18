package com.mszlu.blog.service;

import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.CommentPara;

public interface CommentsService {

	Result commentsArticleById(Long articleId);

	Result comment(CommentPara commentPara);


}
