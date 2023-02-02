package com.mszlu.blog.admin.pojo;

import lombok.Data;

@Data
public class Permission {
	
	private Long id;
	private String path;
	private String description;
	private String name;

}
