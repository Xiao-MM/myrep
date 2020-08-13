package com.ming.service;

import java.util.List;

public interface RolePermissionService {

    /**
     * 给角色分配权限操作
     * @param roleId
     * @param permissionId
     */
    void addPermission(Long roleId,Long permissionId);

    /**
     * 撤销角色权限
     * @param rolePermissionId
     */
    void removePermission(Long rolePermissionId);

}
