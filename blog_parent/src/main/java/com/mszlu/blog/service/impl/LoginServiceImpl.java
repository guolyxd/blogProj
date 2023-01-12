package com.mszlu.blog.service.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.service.LoginService;
import com.mszlu.blog.service.SysUserService;
import com.mszlu.blog.utils.JWTUtils;
import com.mszlu.blog.vo.ErrorCode;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginPara;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private static final String slat = "mszlu!@#";

	@Override
	public Result login(LoginPara loginPara) {
		   /**
         * 1. 检查参数是否合法
         * 2. 根据用户名和密码去user表中查询 是否存在
         * 3. 如果不存在 登录失败
         * 4. 如果存在 ，使用jwt 生成token 返回给前端
         * 5. token放入redis当中，redis  token：user信息 设置过期时间（相比来说session会给服务器产生压力，这么做也是为了实现jwt的续签）
         *  (登录认证的时候 先认证token字符串是否合法，去redis认证是否存在)
         */
		String account = loginPara.getAccount();
		String password = loginPara.getPassword();
		if(StringUtils.isBlank(account)||StringUtils.isBlank(password)) {
			return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
		}

		password = DigestUtils.md5Hex(password + slat);
		SysUser sysUser = sysUserService.findUser(account, password);
		if(sysUser == null) {
			return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
		}
		String token = JWTUtils.createToken(sysUser.getId());
		redisTemplate.opsForValue().set("TOKEN_"+token,JSON.toJSONString(sysUser),1,TimeUnit.DAYS);
		return Result.success(token);
	}

	@Override
	public SysUser check(String token) {
		
		if(StringUtils.isBlank(token)) {
			return null;
		}
		Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
		if(stringObjectMap == null) {
			return null;
		}
		
		String userJson = redisTemplate.opsForValue().get("TOKEN_"+token);
		if(StringUtils.isBlank(userJson)) {
			return null;
		}
		
		SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
		
		
		return sysUser;
	}

	@Override
	public Result logout(String token) {
		redisTemplate.delete("TOKEN_"+token);
		return Result.success(null);
	}



}
