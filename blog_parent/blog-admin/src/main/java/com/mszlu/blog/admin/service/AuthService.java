package com.mszlu.blog.admin.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	
	public boolean auth(HttpServletRequest request, List<GrantedAuthority> authorityList) {
		return true;
	}
	

}
