package com.mszlu.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.dao.mapper.CommentMapper;
import com.mszlu.blog.dao.pojo.Comment;
import com.mszlu.blog.service.CommentsService;
import com.mszlu.blog.service.SysUserService;
import com.mszlu.blog.vo.CommentVo;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.UserVo;

@Service
public class CommentsServiceImpl implements CommentsService{
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired 
	private SysUserService sysUserService;
	
	

	@Override
	public Result commentsArticleById(Long id) {
		
		LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<Comment>();
		queryWrapper.eq(Comment::getArticleId, id);
		queryWrapper.eq(Comment::getLevel,1);
		List<Comment> comments = commentMapper.selectList(queryWrapper);
		List<CommentVo> commentsVo = copyList(comments);
		
		return Result.success(commentsVo);
	}
	
	

	private List<CommentVo> copyList(List<Comment> comments) {
		
		List<CommentVo> commentVoList = new ArrayList<CommentVo>();
		for (Comment comment :comments) {
			commentVoList.add(copy(comment));
		}
		return commentVoList;
	}



	private CommentVo copy(Comment comment) {
		
		CommentVo commentVo = new CommentVo();
		BeanUtils.copyProperties(comment, commentVo);
		
		Long authorId = comment.getAuthorId();
		UserVo userVo = this.sysUserService.findUserVoById(authorId);
		commentVo.setAuthor(userVo);
		
		Integer level = comment.getLevel();
		if(level == 1) {
			Long id = comment.getId();
			List<CommentVo> commentVoList = findCommentsByParentId(id);
			commentVo.setChildrens(commentVoList);
		}
		if(level > 1) {
			Long toUid = comment.getToUid();
			UserVo toUserVo = this.sysUserService.findUserVoById(toUid);
			commentVo.setToUser(toUserVo);
		}
		
		return commentVo;
		
	}


	private List<CommentVo> findCommentsByParentId(Long id) {
		LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Comment::getParentId, id);
		queryWrapper.eq(Comment::getLevel, 2);
		
		return copyList(commentMapper.selectList(queryWrapper));
	}
	

}
