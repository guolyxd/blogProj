package com.mszlu.blog.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.dao.mapper.SysUserMapper;
import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.service.LoginService;
import com.mszlu.blog.service.SysUserService;
import com.mszlu.blog.vo.ErrorCode;
import com.mszlu.blog.vo.LoginUserVo;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.UserVo;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private LoginService loginService;

    @Override
    public SysUser findUserById(Long id) {
        //根据id查询
        //为防止sysUser为空增加一个判断
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("码神之路");
        }
        return sysUser;
    }

	@Override
	public SysUser findUser(String account, String password) {
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysUser::getAccount, account);
		queryWrapper.eq(SysUser::getPassword, password);
		queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
		queryWrapper.last("limit 1");

		return sysUserMapper.selectOne(queryWrapper);
	}

	@Override
	public Result findUserByToken(String token) {
		
		SysUser sysUser = loginService.checkToken(token);
		if(sysUser == null) {
			return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
		}
		
		LoginUserVo loginUserVo = new LoginUserVo();
		loginUserVo.setId(sysUser.getId());
		loginUserVo.setAccount(sysUser.getAccount());
		loginUserVo.setAvatar(sysUser.getAvatar());
		loginUserVo.setNickName(sysUser.getNickname());
		
		return Result.success(loginUserVo);
	}

	@Override
	public SysUser findUserByAccount(String account) {
		LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SysUser::getAccount, account);
		queryWrapper.select(SysUser::getAccount);
		queryWrapper.last("limit 1");
		
		return this.sysUserMapper.selectOne(queryWrapper);
	}

	@Override
	public void save(SysUser sysUser) {
		this.sysUserMapper.insert(sysUser);
		
	}

	@Override
	public UserVo findUserVoById(Long id) {
        //根据id查询
        //为防止sysUser为空增加一个判断
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("码神之路");
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser, userVo);
        return userVo;
	}
}

