package com.ming.service.impl;

import com.ming.dao.RolePermissionMapper;
import com.ming.pojo.RolePermission;
import com.ming.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 给角色分配权限操作
     *
     * @param roleId
     * @param permissionId
     */
    @Override
    public void addPermission(Integer roleId, Integer permissionId) {

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleId);
        rolePermission.setPermissionId(permissionId);

        rolePermissionMapper.insertSelective(rolePermission);
    }

    /**
     * 撤销角色权限
     *
     * @param rolePermissionId
     */
    @Override
    public void removePermission(Integer rolePermissionId) {
        rolePermissionMapper.deleteByPrimaryKey(rolePermissionId);
    }

    /**
     * 查询角色拥有的操作id
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> findPermissionIdsByRoleId(Integer roleId) {
        return rolePermissionMapper.queryPermissionIdsByRoleId(roleId);
    }
}
