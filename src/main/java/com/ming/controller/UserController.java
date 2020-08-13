package com.ming.controller;

import com.ming.dto.UserLoginDTO;
import com.ming.dto.UserRegisterDTO;
import com.ming.exception.ExceptionManager;
import com.ming.pojo.User;
import com.ming.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ExceptionManager exceptionManager;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public String login(@RequestBody @Valid UserLoginDTO userLoginDTO){
        //使用shiro的方式进行登录
        //获取当前对象
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userLoginDTO.getUsername(),userLoginDTO.getPassword());
        try {
            //进入userRealm的认证环节
            subject.login(token);
        }catch (UnknownAccountException e){
            throw exceptionManager.create("EC01000");
        }catch (IncorrectCredentialsException e){
            throw exceptionManager.create("EC01001");
        }
        return "登陆成功!";
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public String register(@RequestBody @Valid UserRegisterDTO userRegisterDTO){
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO,user);
        //生成盐
        String passwordSalt = new SecureRandomNumberGenerator().nextBytes().toHex();
        //生成加密后的密码
        String newPassword = new Md5Hash(userRegisterDTO.getPassword(), passwordSalt, 1024).toString();

        user.setPasswordSalt(passwordSalt);
        user.setPassword(newPassword);
        userService.addUser(user);

        return "注册成功！";
    }


    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal()!=null){
            subject.logout();
        }
        return "您已成功退出登录！";
    }


    @ApiOperation("查看用户")
    @GetMapping("/find/{id}")
    public User findUser(@PathVariable Long id){
        return userService.findUserById(id);
    }

    @ApiOperation("查看当前已经登陆的用户")
    @GetMapping("/show")
    public String showLoginUser(){
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        log.info("current user is ------->"+username);
        return username;
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/update")
    public String updateUser(@RequestBody User user){
        userService.updateUser(user);
        return "更改用户信息成功！";
    }
}
