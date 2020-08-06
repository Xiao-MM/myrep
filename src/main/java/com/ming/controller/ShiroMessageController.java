package com.ming.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShiroMessageController {
    /**
     * 成功链接
     * @return
     */
    @GetMapping("/success")
    public String success(){
        return "success";
    }

    /**
     * 未授权
     * @return
     */
    @GetMapping("/unauthorized")
    public String unauthorized(){
        return "unauthorized";
    }
}
