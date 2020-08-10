package com.ming.service.impl;

import com.ming.dao.UserMapper;
import com.ming.pojo.Role;
import com.ming.pojo.User;
import com.ming.service.RoleService;
import com.ming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;

    @Override
    public User findUserByUsername(String username) {

        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username",username);

        List<User> users = userMapper.selectByExample(example);

        if (users!=null&&users.size()>0){
            return users.get(0);
        }
        return null;
    }

    @Override
    @Cacheable(value = "user",key = "#id")
    public User findUserById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        List<Role> roles = roleService.findRoles(id);
        user.setRoles(roles);
        return user;
    }

    @Override
    @CacheEvict(value = "user",key = "#user.getId()")
    public void updateUser(User user) {
        System.out.println(user);
        userMapper.updateByPrimaryKeySelective(user);
    }


}
