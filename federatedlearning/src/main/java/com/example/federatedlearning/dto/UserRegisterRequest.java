package com.example.federatedlearning.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterRequest {

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^\\S{5,16}$", message = "用户名长度必须在5到16个字符之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^\\S{5,16}$", message = "密码长度必须在5到16个字符之间")
    private String password;

    private String location;

    @Pattern(regexp = "^(active|offline)$", message = "状态必须是 active 或 offline")
    private String status;

    private String registrationName;

    @Pattern(regexp = "^(enterprise|institution)$", message = "类型必须是 enterprise 或 institution")
    private String type;

    private String role;
    private String contact;

}