package com.ming.service.impl;

import com.ming.dao.UserRoleMapper;
import com.ming.pojo.UserRole;
import com.ming.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    /**
     * 给用户分配角色
     *
     * @param userId
     * @param roleId
     */
    @Override
    public void addRole(Integer userId, Integer roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleMapper.insertSelective(userRole);
    }

    /**
     * 撤销用户角色
     *
     * @param userRoleId
     */
    @Override
    public void removeRole(Integer userRoleId) {
        userRoleMapper.deleteByPrimaryKey(userRoleId);
    }
}
