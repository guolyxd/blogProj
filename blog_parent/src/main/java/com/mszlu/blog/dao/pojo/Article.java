package com.mszlu.blog.dao.pojo;

import lombok.Data;

@Data
public class Article {


	public static final int Article_TOP = 1;
	public static final int Article_Common = 0;

	private Long id;
	private String title;
	private String summary;
	private int viewCounts;
	private int commentCounts;

	private Long authorId;
	private Long categoryId;

	private int weight = Article_Common;
	private Long createDate;


}
