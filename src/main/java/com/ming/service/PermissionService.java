package com.ming.service;

import com.ming.pojo.Permission;

import java.util.List;

public interface PermissionService {

    boolean isPermissionExist(Long permissionId);

    void addPermission(Permission permission);

    void delPermission(Long permissionId);

    void updatePermission(Permission permission);

    Permission findPermission(Long permissionId);

    List<Permission> findPermissions();

    List<Permission> findPermissions(Long roleId);
}
