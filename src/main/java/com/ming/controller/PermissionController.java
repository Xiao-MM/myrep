package com.ming.controller;

import com.ming.dto.PermissionDTO;
import com.ming.pojo.Permission;
import com.ming.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    @GetMapping("/delete/{permissionId}")
    public String delPermission(@PathVariable Long permissionId){
        permissionService.delPermission(permissionId);
        return "删除权限成功！";
    }

    @RequiresRoles("admin")
    @ApiOperation("更新权限")
    @PostMapping("/update")
    public String updatePermission(@RequestBody Permission permission){
        permissionService.updatePermission(permission);
        return "删除权限成功！";
    }

    @RequiresRoles("admin")
    @ApiOperation("查看权限")
    @GetMapping("/find/{permissionId}")
    public Permission findPermission(@PathVariable Long permissionId){
        return permissionService.findPermission(permissionId);
    }

    @RequiresRoles(value = {"admin"})
    @ApiOperation("查找所有权限")
    @GetMapping("/find/all")
    public List<Permission> findAllPermissions(){
        return permissionService.findAllPermissions();
    }

}
