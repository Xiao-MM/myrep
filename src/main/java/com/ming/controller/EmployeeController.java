package com.ming.controller;

import com.ming.annotation.RequiredPermission;
import com.ming.pojo.Employee;
import com.ming.service.EmployeeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    @RequiredPermission("添加权限")
    public String addEmployee(@RequestBody Employee employee){
        employeeService.addEmployee(employee);
        return "添加员工成功！";
    }

    @GetMapping("/view/{id}")
    @RequiredPermission("查找权限")
    public Employee findEmployee(@PathVariable Integer id){
        return employeeService.findEmployee(id);
    }

    @GetMapping("/to_login")
    public String toLogin(){
        return "shiro拦截成功转向的请求";
    }

    @GetMapping("/login/{username}/{password}")
    public String login(@PathVariable String username,@PathVariable String password){
        //1. 获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //2. 封装用户数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        //3. 执行用户登录
        try {
            subject.login(usernamePasswordToken);
        }catch (UnknownAccountException e){
            return "用户不存在";
        }catch (IncorrectCredentialsException e){
            return "密码错误";
        }
        return "登陆成功！";
    }

    @GetMapping("/unAuth")
    public String unAuth(){
        return "亲！您未被授权！";
    }
}
