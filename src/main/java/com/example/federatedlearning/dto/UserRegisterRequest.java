package com.example.federatedlearning.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class UserRegisterRequest {

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^\\S{3,16}$", message = "用户名长度必须在3到16个字符之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*]).{8,16}$",
            message = "密码长度必须在8到16个字符之间,必须含有大小写字母，数字，特殊字符：!@#$%&*")
    private String password;

//    @NotBlank(message = "确认密码不能为空")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*]).{8,16}$",
//            message = "密码长度必须在8到16个字符之间,必须含有大小写字母，数字，特殊字符：!@#$%&*")
//    private String password_2; // 确认密码字段

    private String location;

//    @Pattern(regexp = "^(enterprise|institution)$", message = "类型必须是 enterprise 或 institution")
    private String type;

    private String role;

    private String contact;

}