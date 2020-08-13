package com.ming.service;

import com.ming.pojo.Permission;

import java.util.List;

public interface PermissionService {

    boolean isPermissionExist(Integer permissionId);

    void addPermission(Permission permission);

    void delPermission(Integer permissionId);

    void updatePermission(Permission permission);

    Permission findPermission(Integer permissionId);

    List<Permission> findPermissions();

    List<Permission> findPermissions(Integer roleId);
}
