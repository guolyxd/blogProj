package com.mszlu.blog.admin.vo;

import java.util.List;
import lombok.Data;


@Data
public class PageResult<T> {
	private List<T> list;
	private Long total;

}
