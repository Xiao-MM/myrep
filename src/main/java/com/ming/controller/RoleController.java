package com.ming.controller;

import com.ming.dto.RoleDTO;
import com.ming.pojo.Role;
import com.ming.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @ApiOperation("添加角色")
    @PostMapping("/add")
    public String addRole(@RequestBody RoleDTO roleDTO){
        roleService.addRole(roleDTO);
        return "添加角色成功！";
    }


    @ApiOperation("查看角色")
    @GetMapping("/find/list")
    public List<Role> findRoles(){
        return roleService.findRoles();
    }
}
