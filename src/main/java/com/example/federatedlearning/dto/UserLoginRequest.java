package com.example.federatedlearning.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserLoginRequest {

    @Pattern(regexp = "^\\S{3,16}$", message = "用户名长度必须在3到16个字符之间")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*]).{8,16}$",
            message = "密码长度必须在8到16个字符之间,必须含有大小写字母，数字，特殊字符：!@#$%&*")
    private String password;

}