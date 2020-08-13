package com.ming.dao;

import com.ming.pojo.UserRole;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserRoleMapper extends Mapper<UserRole> {

    @Select("select role_id from user_role where user_id = #{userId}")
    List<Long> queryRoleIdsByUserId(Long userId);
}
