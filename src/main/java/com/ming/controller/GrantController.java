package com.ming.controller;

import com.ming.service.RolePermissionService;
import com.ming.service.UserRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grant")
public class GrantController {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @ApiOperation("角色分配权限")
    @GetMapping("/resource/add/{roleId}/{resourceId}")
    public String addResource(@PathVariable Long roleId, @PathVariable Long resourceId){
        rolePermissionService.addPermission(roleId,resourceId);
        return "角色分配权限成功";
    }
    @ApiOperation("用户分配角色")
    @GetMapping("/role/add/{userId}/{roleId}")
    public String addRole(@PathVariable Long userId, @PathVariable Long roleId){
        userRoleService.addRole(userId,roleId);
        return "用户分配角色成功！";
    }

}
