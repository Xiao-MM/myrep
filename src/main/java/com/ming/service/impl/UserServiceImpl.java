package com.ming.service.impl;

import com.ming.dao.UserMapper;
import com.ming.exception.ExceptionManager;
import com.ming.pojo.Role;
import com.ming.pojo.User;
import com.ming.service.RoleService;
import com.ming.service.UserService;
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
    @Resource
    private RoleService roleService;
    @Resource
    private ExceptionManager exceptionManager;

    @Override
    public boolean isUserExist(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user!=null&& user.getDeleted().equals(User.EXIST);
    }

    @Override
    public User addUser(User user) {
        userMapper.insertSelective(user);
        return user;
    }

    @Override
    public void deleteUser(Long userId) {
        if (!this.isUserExist(userId)){
            throw exceptionManager.create("EC01000");
        }
        User user = new User();
        user.setId(userId);
        user.setDeleted(System.currentTimeMillis());
        userMapper.updateByPrimaryKeySelective(user);
    }

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
    public User findUserById(Long id) {
        User user = userMapper.findUserById(id);
        List<Role> roles = roleService.findRolesById(id);
        user.setRoles(roles);
        return user;
    }

    @Override
    @CacheEvict(value = "user",key = "#user.getId()")
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @Override
    public List<User> findAllUsers() {
        return userMapper.findAllUsers();
    }
}
