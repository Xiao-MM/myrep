package com.ming.service;

import com.ming.pojo.Permission;

import java.util.List;

public interface PermissionService {

    void addPermission(Permission permission);

    void delPermission(Integer permissionId);

    void updatePermission(Permission permission);

    Permission findPermission(Integer permissionId);

    List<Permission> findPermissions();

    List<Permission> findPermissionsByRoleId(Integer roleId);
}
