package com.ming.dao;

import com.ming.pojo.Role;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {

    @Select("select r.* from role r inner join user_role ur on r.id = ur.role_id where ur.user_id = #{userId}")
    List<Role> findRolesByUserId(Long userId);
}
