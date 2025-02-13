package com.example.federatedlearning.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserLoginRequest {

    @Pattern(regexp = "^\\S{5,16}$", message = "用户名长度必须在5到16个字符之间")
    private String username;

    @Pattern(regexp = "^\\S{5,16}$", message = "密码长度必须在5到16个字符之间")
    private String password;

}