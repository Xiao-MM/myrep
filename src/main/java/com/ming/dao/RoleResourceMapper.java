package com.ming.dao;

import com.ming.pojo.RoleResource;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleResourceMapper extends Mapper<RoleResource> {

    @Select("select resource_id from role_resource where role_id = #{roleId}")
    List<Integer> findResourceIdsByRoleId(Integer roleId);
}
