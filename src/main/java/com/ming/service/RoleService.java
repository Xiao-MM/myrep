package com.ming.service;

import com.ming.dto.RoleDTO;
import com.ming.pojo.Role;

import java.util.List;

public interface RoleService {
    /**
     * 判断角色是否存在
     * @param roleId
     * @return
     */
    boolean isRoleExist(Long roleId);

    void addRole(RoleDTO roleDTO);

    void deleteRole(Long roleId);

    void updateRole(Role role);

    Role findRole(Long roleId);

    List<Role> findRoles();

    /**
     * 查找用户包含的角色
     * @param userId
     * @return
     */
    List<Role> findRolesById(Long userId);
}
