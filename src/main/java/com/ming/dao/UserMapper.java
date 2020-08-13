package com.ming.dao;

import com.ming.pojo.User;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;
@CacheNamespace//开启mybatis二级缓存
public interface UserMapper extends Mapper<User> {

}
