package com.ming.service;

import com.ming.pojo.Role;

import java.util.List;

public interface RoleService {

    void addRole(Role role);

    void delRole(Integer roleId);

    void updateRole(Role role);

    Role findRole(Integer roleId);

    List<Role> findRoles();
}
