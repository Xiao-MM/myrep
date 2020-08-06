package com.ming.controller;

import com.ming.pojo.Permission;
import com.ming.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @ApiOperation("添加操作")
    @PostMapping("/add")
    public String addResource(@RequestBody Permission permission){
        permissionService.addPermission(permission);
        return "添加操作成功！";
    }

    @ApiOperation("查看操作")
    @GetMapping("/find")
    public List<Permission> findResources(){
        return permissionService.findPermissions();
    }
}
