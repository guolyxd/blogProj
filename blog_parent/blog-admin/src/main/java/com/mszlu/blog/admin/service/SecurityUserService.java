package com.mszlu.blog.admin.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.mszlu.blog.admin.pojo.Admin;


/* author email : guolyxd@163.com  */
/* Date: 2023/02/03  */


@Component
public class SecurityUserService implements UserDetailsService {
	
	@Autowired
	private AdminService adminService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		Admin admin = this.adminService.findUserByUsername(username);
		if(admin == null) {
			return null;
		}
		
		UserDetails userDetails = new User(username, admin.getPassword(),new ArrayList<>());
		return userDetails;
	}

}
