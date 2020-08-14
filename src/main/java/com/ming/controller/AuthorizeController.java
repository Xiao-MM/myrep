package com.ming.controller;

import com.ming.service.RolePermissionService;
import com.ming.service.UserRoleService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorize")
public class AuthorizeController {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @RequiresRoles("admin")
    @ApiOperation("角色分配权限")
    @GetMapping("/permission/add/{roleId}/{permissionId}")
    public String addResource(@PathVariable Long roleId, @PathVariable Long permissionId){
        rolePermissionService.addPermission(roleId,permissionId);
        return "角色分配权限成功";
    }

    @RequiresRoles("admin")
    @ApiOperation("撤销角色权限")
    @GetMapping("/permission/remove/{rolePermissionId}")
    public void removePermission(@PathVariable Long rolePermissionId) {
        rolePermissionService.removePermission(rolePermissionId);
    }

    @RequiresRoles("admin")
    @ApiOperation("用户分配角色")
    @GetMapping("/role/add/{userId}/{roleId}")
    public String addRole(@PathVariable Long userId, @PathVariable Long roleId){
        userRoleService.addRole(userId,roleId);
        return "用户分配角色成功！";
    }

    @RequiresRoles("admin")
    @ApiOperation("撤销用户角色")
    @GetMapping("/role/remove/{userRoleId}")
    public String removeRole(@PathVariable Long userRoleId) {
        userRoleService.removeRole(userRoleId);
        return "角色撤销成功！";
    }
}
