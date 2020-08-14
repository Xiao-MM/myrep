package com.ming.controller;

import org.apache.shiro.authz.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionTestController {
    /**
     * 需要验证才能访问的api
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/requiresAuthentication")
    public String requiresAuthentication(){
        return "requiresAuthentication";
    }

    /**
     * 需要用户身份才能访问的api
     * @return
     */
    @RequiresUser
    @GetMapping("/requireUser")
    public String requireUser(){
        return "requireUser";
    }
    /**
     * 需要guest身份才能访问的api
     * @return
     */
    @RequiresGuest
    @GetMapping("/RequiresGuest")
    public String requireGuest(){
        return "RequiresGuest";
    }
    /**
     * 需要admin身份才能访问的api
     * @return
     */
    @RequiresRoles("admin")
    @GetMapping("/requireRoles")
    public String requireRoles(){
        return "RequiresGuest";
    }

    /**
     * 需要add权限才能访问的api
     * @return
     */
    @RequiresPermissions("user_add")
    @GetMapping("/requirePermissionsAdd")
    public String requirePermissionsAdd(){
        return "RequiresPermissions";
    }
    /**
     * 需要add权限才能访问的api
     * @return
     */
    @RequiresPermissions("user_delete")
    @GetMapping("/requirePermissionsDelete")
    public String requirePermissionsDelete(){
        return "requirePermissionsDelete";
    }

    /**
     * 公开接口
     * @return
     */
    @GetMapping("/open/api/sayHello")
    public String sayHello(){
        return "open api! Any one can access!";
    }

}
