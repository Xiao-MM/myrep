package com.ming.controller;

import com.ming.annotation.UserLoginToken;
import com.ming.pojo.User;
import com.ming.service.UserService;
import com.ming.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login/{username}/{password}")
    public Map<String,Object> login(@PathVariable String username, @PathVariable String password){

        User user = userService.findUserByUsername(username);
        Map<String,Object> map = new HashMap<>();

        if (user == null){
            map.put("msg","用户不存在");
            return map;
        }

        if (!(user.getPassword().equals(password))){
            map.put("msg","密码错误");
            return map;
        }

        String token = JWTUtil.getToken(user);
        System.out.println(token);
        map.put("token",token);
        map.put("user",user);
        map.put("msg","登陆成功!");
        return map;
    }

    @GetMapping("/find/{id}")
    public User findUser(@PathVariable Integer id){
        return userService.findUserById(id);
    }

    @PostMapping("/update")
    public String updateUser(@RequestBody User user){
        userService.updateUser(user);
        return "更改用户信息成功！";
    }
    //加token才能访问
    @GetMapping("/getMessage")
    @UserLoginToken
    public String getMessage(){
        return "您已通过验证！";
    }
}
