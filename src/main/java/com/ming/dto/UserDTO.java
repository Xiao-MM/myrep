package com.ming.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {
    @NotEmpty(message = "用户名不能为空")
    private String username;
}
