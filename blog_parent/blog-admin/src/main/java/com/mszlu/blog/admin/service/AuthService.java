package com.mszlu.blog.admin.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.mszlu.blog.admin.pojo.Admin;
import com.mszlu.blog.admin.pojo.Permission;

/* author email : guolyxd@163.com  */
/* Date: 2023/02/03  */


@Service
public class AuthService {
	
	@Autowired
	private AdminService adminService; 
	
	public boolean auth(HttpServletRequest request, Authentication authentication) {
		
		String requestURI = request.getRequestURI();
		Object principal = authentication.getPrincipal();
		if(principal == null || "anonymousUser".equals(principal)) {
			return false;
		}
		
		UserDetails userDetails = (UserDetails) principal;
		String username = userDetails.getUsername();
		Admin admin = adminService.findUserByUsername(username);
		if(admin == null) {
			return false;
		}
		if(admin.getId()==1) {
			return true;
		}
		
		Long id = admin.getId();
		List<Permission> permissionList = this.adminService.findPermissionByAdminId(id);
		requestURI = StringUtils.split(requestURI, '?')[0];
		for(Permission permission:permissionList) {
			if(requestURI.equals(permission.getPath())) {
				return true;
			}
		}
		return true;
	}
	

}
