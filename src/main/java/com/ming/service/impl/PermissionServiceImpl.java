package com.ming.service.impl;

import com.ming.dao.PermissionMapper;
import com.ming.dao.RolePermissionMapper;
import com.ming.pojo.Permission;
import com.ming.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public void addPermission(Permission permission) {
        permissionMapper.insertSelective(permission);
    }

    @Override
    public void delPermission(Integer permissionId) {
        permissionMapper.deleteByPrimaryKey(permissionId);
    }

    @Override
    public void updatePermission(Permission permission) {
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public Permission findPermission(Integer permissionId) {
        return permissionMapper.selectByPrimaryKey(permissionId);
    }

    @Override
    public List<Permission> findPermissions() {
        return permissionMapper.selectAll();
    }

    @Override
    public List<Permission> findPermissionsByRoleId(Integer roleId) {
        //查找出该角色拥有的所有权限的id
        List<Integer> permissionIds = rolePermissionMapper.queryPermissionIdsByRoleId(roleId);
        //根据这些id查询出所有的权限信息
        return permissionMapper.queryPermissionsInPermissionIds(permissionIds);
    }


}
