package com.ming.controller;

import com.ming.dto.PermissionDTO;
import com.ming.pojo.Permission;
import com.ming.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @RequiresRoles("admin")
    @ApiOperation("添加权限")
    @PostMapping("/add")
    public String addPermission(@RequestBody PermissionDTO permissionDTO){
        permissionService.addPermission(permissionDTO);
        return "添加操作成功！";
    }

    @RequiresRoles("admin")
    @ApiOperation("删除权限")
    @DeleteMapping("/delete")
    public String delPermission(Long permissionId){
        permissionService.delPermission(permissionId);
        return "删除权限成功！";
    }

    @RequiresRoles("admin")
    @ApiOperation("更新权限")
    @DeleteMapping("/update")
    public String updatePermission(Permission permission){
        permissionService.updatePermission(permission);
        return "删除权限成功！";
    }

    @RequiresRoles("admin")
    @ApiOperation("查看权限")
    @DeleteMapping("/find")
    public Permission findPermission(Long permissionId){
        return permissionService.findPermission(permissionId);
    }


}
