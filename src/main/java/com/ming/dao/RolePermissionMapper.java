package com.ming.dao;

import com.ming.pojo.RolePermission;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RolePermissionMapper extends Mapper<RolePermission> {

    @Select("select permission_id from role_permission where role_id = #{roleId}")
    List<Long> queryPermissionIdsByRoleId(Long roleId);
}
