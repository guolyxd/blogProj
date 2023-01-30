package com.mszlu.blog.controller;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mszlu.blog.utils.QiniuUtils;
import com.mszlu.blog.vo.Result;

@RestController
@RequestMapping("upload")
public class UploadController {
	
	@Autowired
	private QiniuUtils qiniuUtils;
	
	@PostMapping
	public Result upload(@RequestParam("image") MultipartFile file ){
		String originalFilename = file.getOriginalFilename();
		String fileName = UUID.randomUUID().toString()+"."+StringUtils.substringAfterLast(originalFilename, ".");
		boolean upload = qiniuUtils.upload(file, fileName);
		if(upload) {
			return Result.success(qiniuUtils.url+fileName);
		}
		
		return Result.fail(20001, "Upload image fail");
	}

}
