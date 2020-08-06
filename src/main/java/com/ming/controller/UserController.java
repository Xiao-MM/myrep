package com.ming.controller;

import com.ming.exception.ExceptionManager;
import com.ming.pojo.User;
import com.ming.service.UserService;
import com.ming.utils.JWTUtil;
import com.ming.utils.RedisUtil;
import com.ming.vo.UserVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ExceptionManager exceptionManager;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public UserVO login(String username, String password){

        User user = userService.findUserByUsername(username);
        if (user == null){
            throw exceptionManager.create("EC01000");
        }
        if (!(user.getPassword().equals(password))){
            throw exceptionManager.create("EC01001");
        }
        //成功登录一次后就将用户信息按照(id:user)存入redis
        //redisUtil.set(user.getId().toString(),user);
        String token = JWTUtil.generateToken(user);
        UserVO userVO = new UserVO();
        userVO.setUser(user);
        userVO.setToken(token);

        return userVO;
    }

    @ApiOperation("查看用户")
    @GetMapping("/find/{id}")
    public User findUser(@PathVariable Integer id){
        return userService.findUserById(id);
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/update")
    public String updateUser(@RequestBody User user){
        userService.updateUser(user);
        return "更改用户信息成功！";
    }


    //加token才能访问
    @ApiOperation("token校验")
    @GetMapping("/getMessage")
    //@UserLoginToken
    public String getMessage(){
        return "您已通过验证！";
    }
}
