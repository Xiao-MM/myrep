package com.ming.service;

import com.ming.pojo.Role;

import java.util.List;

public interface RoleService {

    void addRole(Role role);

    void delRole(Long roleId);

    void updateRole(Role role);

    Role findRole(Long roleId);

    List<Role> findRoles();

    /**
     * 查找用户包含的角色
     * @param userId
     * @return
     */
    List<Role> findRoles(Long userId);
}
