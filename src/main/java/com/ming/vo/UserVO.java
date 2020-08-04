package com.ming.vo;

import com.ming.pojo.User;
import lombok.Data;

@Data
public class UserVO {
    private User user;
    private String token;
}
