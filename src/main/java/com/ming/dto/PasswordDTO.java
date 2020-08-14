package com.ming.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PasswordDTO {
    @NotEmpty(message = "密码不能为空")
    private String password;
}
