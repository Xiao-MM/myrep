package com.ming.controller;

import com.ming.exception.ExceptionManager;
import com.ming.pojo.User;
import com.ming.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ExceptionManager exceptionManager;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public String login(String username, String password){
        //使用shiro的方式进行登录
        //获取当前对象
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
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

    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal()!=null){
            subject.logout();
        }
        return "已成功退出登录！";
    }


    @ApiOperation("查看用户")
    @GetMapping("/find/{id}")
    public User findUser(@PathVariable Integer id){
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

//    @ApiOperation("用户登录")
//    @PostMapping("/login")
//    public UserVO login(String username, String password){
//
//        User user = userService.findUserByUsername(username);
//        if (user == null){
//            throw exceptionManager.create("EC01000");
//        }
//        if (!(user.getPassword().equals(password))){
//            throw exceptionManager.create("EC01001");
//        }
//        //成功登录一次后就将用户信息按照(id:user)存入redis
//        //redisUtil.set(user.getId().toString(),user);
//        String token = JWTUtil.generateToken(user);
//        String sessionId = (String) request.getSession().getAttribute("sessionId");
//        UserVO userVO = new UserVO();
//        userVO.setUser(user);
//        userVO.setToken(token);
//        userVO.setSessionId(sessionId);
//        return userVO;
//    }
}
