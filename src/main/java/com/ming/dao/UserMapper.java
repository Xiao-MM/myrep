package com.ming.dao;

import com.ming.pojo.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@CacheNamespace//开启mybatis二级缓存
public interface UserMapper extends Mapper<User> {

    @Select("select * from user where id = #{userId} and deleted = 0")
    User findUserById(Long userId);

    @Select("select * from user where deleted = 0")
    List<User> findAllUsers();
}
