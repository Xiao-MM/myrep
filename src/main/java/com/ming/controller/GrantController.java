package com.ming.controller;

import com.ming.service.RoleResourceService;
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
    private RoleResourceService roleResourceService;

    @ApiOperation("角色分配权限")
    @GetMapping("/resource/add/{roleId}/{resourceId}")
    public String addResource(@PathVariable Integer roleId, @PathVariable Integer resourceId){
        roleResourceService.addResource(roleId,resourceId);
        return "角色分配权限成功";
    }
    @ApiOperation("用户分配角色")
    @GetMapping("/role/add/{userId}/{roleId}")
    public String addRole(@PathVariable Integer userId, @PathVariable Integer roleId){
        userRoleService.addRole(userId,roleId);
        return "用户分配角色成功！";
    }

}
