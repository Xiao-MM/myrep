package com.ming.service.impl;

import com.ming.dao.RoleMapper;
import com.ming.pojo.Role;
import com.ming.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void addRole(Role role) {
        roleMapper.insertSelective(role);
    }

    @Override
    public void delRole(Integer roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public Role findRole(Integer roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public List<Role> findRoles() {
        return roleMapper.selectAll();
    }
}
