package com.ming.service.impl;

import com.ming.dao.RolePermissionMapper;
import com.ming.pojo.RolePermission;
import com.ming.service.RolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 给角色分配权限操作
     *
     * @param roleId
     * @param permissionId
     */
    @Override
    public void addPermission(Long roleId, Long permissionId) {

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
    public void removePermission(Long rolePermissionId) {
        rolePermissionMapper.deleteByPrimaryKey(rolePermissionId);
    }
}
