package com.mszlu.blog.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.admin.pojo.Admin;
import com.mszlu.blog.admin.pojo.Permission;



/* author email : guolyxd@163.com  */
/* Date: 2023/02/03  */
/* Admin page username/password : admin/123456 */

public interface AdminMapper extends BaseMapper<Admin>{

	@Select("select * from ms_permission where id in (select permission_id from ms_admin_permission where admin_id=#{adminId})")
	List<Permission> findPermissionByAdminId(Long adminId);

}
