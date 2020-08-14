package com.ming.controller;

import com.ming.dto.PasswordDTO;
import com.ming.dto.UserDTO;
import com.ming.dto.UserLoginDTO;
import com.ming.dto.UserRegisterDTO;
import com.ming.exception.ExceptionManager;
import com.ming.pojo.User;
import com.ming.service.UserService;
import com.ming.utils.RedisUtil;
import com.ming.utils.TimeUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * new StandardManager()
 * tomcat的StandardManager类将session存储在内存中，也可以持久化到file，数据库，memcache，redis等
 * @author zm
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserService userService;
    @Resource
    private ExceptionManager exceptionManager;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public String login(@RequestBody @Valid UserLoginDTO userLoginDTO){
        userLogin(userLoginDTO.getUsername(),userLoginDTO.getPassword());
        return "登陆成功!";
    }

    /**
     * 封装登录方法
     * @param username
     * @param password
     * @return
     */
    private void userLogin(String username,String password){
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
        User user = userService.findUserByUsername(username);
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("id",user.getId());
        map.put("username",username);
        map.put("password",password);
        redisUtil.hashMoreSet("user",map);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public String register(@RequestBody @Valid UserRegisterDTO userRegisterDTO){
        if (userService.findUserByUsername(userRegisterDTO.getUsername())!=null){
            throw exceptionManager.create("EC01006");
        }
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO,user);
        //生成盐
        String passwordSalt = new SecureRandomNumberGenerator().nextBytes().toHex();
        //生成加密后的密码
        String newPassword = new Md5Hash(userRegisterDTO.getPassword(), passwordSalt, 1024).toString();

        user.setPasswordSalt(passwordSalt);
        user.setPassword(newPassword);
        user.setCreateTime(TimeUtil.getCurrentTime());
        userService.addUser(user);

        return "注册成功！";
    }

    @RequiresAuthentication
    @ApiOperation("修改密码")
    @PostMapping("/password/update")
    @Transactional
    public String updatePassword(@RequestBody @Valid PasswordDTO passwordDTO){
        //生成盐
        String passwordSalt = new SecureRandomNumberGenerator().nextBytes().toHex();
        //生成加密后的密码
        String newPassword = new Md5Hash(passwordDTO.getPassword(), passwordSalt, 1024).toString();

        String currentUsername = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findUserByUsername(currentUsername);

        user.setPassword(newPassword);
        user.setPasswordSalt(passwordSalt);
        userService.updateUser(user);
        //修改完用户名后当前用户退出登录
        SecurityUtils.getSubject().logout();
        //重新以新密码登录
        Map<Object, Object> oldUser = redisUtil.hashMoreGet("user");
        String username = (String) oldUser.get("username");
        userLogin(username,passwordDTO.getPassword());
        return "密码修改成功！";
    }

    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal()!=null){
            subject.logout();
        }
        //清除掉redis存储的用户信息缓存
        redisUtil.del("user");
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
    public String updateUser(@RequestBody @Valid UserDTO userDTO){
        Subject subject = SecurityUtils.getSubject();
        String oldUserName = subject.getPrincipal().toString();
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);

        Map<Object, Object> oldUser = redisUtil.hashMoreGet("user");
        //redis存Long取得是Integer
        Integer intUserId = (Integer)oldUser.get("id");
        Long userId = intUserId.longValue();
        user.setId(userId);
        userService.updateUser(user);

        //如果用户名发生了修改
        String username = userDTO.getUsername();
        if (!StringUtils.isEmpty(username) && !username.equals(oldUserName)){
            //修改完用户名后当前用户退出登录
            SecurityUtils.getSubject().logout();
            //重新以新用户名密码登录

            String password = (String) oldUser.get("password");
            userLogin(username,password);
        }
        return "更改用户信息成功！";
    }
}
