package com.ming.dao;

import com.ming.pojo.Permission;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PermissionMapper extends Mapper<Permission> {

    @Select("select p.* from permission p inner join role_permission rp on p.id = rp.permission_id where rp.role_id = #{roleId}")
    List<Permission> findPermissionsByRoleId(Long roleId);
}
