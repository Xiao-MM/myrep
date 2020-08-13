package com.ming.service;

import com.ming.dto.PermissionDTO;
import com.ming.dto.PermissionFindDTO;
import com.ming.pojo.Permission;
import com.ming.vo.PageVO;

import java.util.List;

public interface PermissionService {

    /**
     * 判断权限是否存在
     * @param permissionId
     * @return
     */
    boolean isPermissionExist(Long permissionId);

    /**
     * 添加权限
     * @param permissionDTO
     */
    void addPermission(PermissionDTO permissionDTO);

    /**
     * 删除权限
     * @param permissionId
     */
    void delPermission(Long permissionId);

    /**
     * 更新权限
     * @param permission
     */
    void updatePermission(Permission permission);

    /**
     * 根据id查询权限
     * @param permissionId
     * @return
     */
    Permission findPermission(Long permissionId);

    /**
     * 查找所有权限
     * @return
     */
    List<Permission> findAllPermissions();
    /**
     * 查找权限
     * @param permissionFindDTO
     * @return
     */
    PageVO<Permission> findPermissions(PermissionFindDTO permissionFindDTO);

    /**
     * 根据角色id查询权限
     * @param roleId
     * @return
     */
    List<Permission> findPermissionsByRoleId(Long roleId);
}
