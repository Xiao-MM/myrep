package com.ming.dao;

import com.ming.pojo.RolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RolePermissionMapper extends Mapper<RolePermission> {

    /**
     * 根据角色id查找对应的权限id
     * @param roleId
     * @return
     */
    @Select("select permission_id from role_permission where role_id = #{roleId}")
    List<Long> findPermissionIdsByRoleId(Long roleId);

    /**
     * 根据角色id删除对应的权限关系映射
     * @param roleId
     */
    @Delete("delete from role_permission where role_id = #{roleId}")
    void deleteRolePermissionsByRoleId(Long roleId);
}
