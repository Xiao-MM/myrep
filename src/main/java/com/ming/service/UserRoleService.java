package com.ming.service;

import java.util.List;

public interface UserRoleService {
    /**
     * 给用户分配角色
     * @param userId
     * @param roleId
     */
    void addRole(Long userId,Long roleId);

    /**
     * 撤销用户角色
     * @param userRoleId
     */
    void removeRole(Long userRoleId);

}
