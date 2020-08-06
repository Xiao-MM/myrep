package com.ming.service;

import java.util.List;

public interface RolePermissionService {

    /**
     * 给角色分配权限操作
     * @param roleId
     * @param permissionId
     */
    void addPermission(Integer roleId,Integer permissionId);

    /**
     * 撤销角色权限
     * @param rolePermissionId
     */
    void removePermission(Integer rolePermissionId);

    /**
     * 查询角色拥有的操作id
     * @return
     */
    List<Integer> findPermissionIdsByRoleId(Integer roleId);

}
