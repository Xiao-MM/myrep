package com.ming.service;

import java.util.List;

public interface RoleResourceService {

    /**
     * 给角色分配权限操作
     * @param roleId
     * @param resourceId
     */
    void addResource(Integer roleId,Integer resourceId);

    /**
     * 撤销角色权限
     * @param roleResourceId
     */
    void removeResource(Integer roleResourceId);

    /**
     * 查询角色拥有的操作id
     * @return
     */
    List<Integer> findResourceIdsByRoleId(Integer roleId);

}
