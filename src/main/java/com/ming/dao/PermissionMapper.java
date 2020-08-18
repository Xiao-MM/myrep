package com.ming.dao;

import com.ming.pojo.Permission;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PermissionMapper extends Mapper<Permission> {

    @Select("select * from permission where id = #{permissionId} and deleted = 0")
    Permission findPermission(Long permissionId);

    @Select("select * from permission where deleted = 0")
    List<Permission> findAll();

    @Select("select p.* from permission p inner join role_permission rp on p.id = rp.permission_id where rp.role_id = #{roleId} and p.deleted=0")
    List<Permission> findPermissionsByRoleId(Long roleId);
}
