package com.ming.controller;

import com.ming.pojo.Permission;
import com.ming.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation("添加操作")
    @PostMapping("/add")
    @RequiresRoles("admin")
    public String addPermission(@RequestBody Permission permission){
        permissionService.addPermission(permission);
        return "添加操作成功！";
    }
    @ApiOperation("添加操作")
    @DeleteMapping("/delete")
    @RequiresRoles("admin")
    public String delPermission(Integer permissionId){
        permissionService.delPermission(permissionId);
        return "删除权限成功！";
    }

    @RequiresRoles({"admin","manager"})
    @ApiOperation("查看操作")
    @GetMapping("/find")
    public List<Permission> findPermissions(){
        return permissionService.findPermissions();
    }



}
