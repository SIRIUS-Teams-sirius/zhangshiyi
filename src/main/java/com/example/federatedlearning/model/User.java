package com.example.federatedlearning.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User {
    @NonNull
    private int id;
    @NotEmpty //必须填上登记的名字
    @Pattern(regexp = "^\\S{1,10}$") //限制登记的名字字符数为1-10
    private String name;

    @JsonIgnore//让springmvc把当前对象转换成json字符串的时候忽略password
    private String password;
    private String location;
    private String status;//状态 ENUM('active', 'offline') NOT NULL，

    @NotEmpty //必须填上登记的名字
    @Pattern(regexp = "^\\S{1,10}$") //限制登记的名字字符数为1-10
    private LocalDate registration_name;

    private String type;//类型 ENUM（‘企业’，‘机构’）NOT NULL，
    private String role;
    private String contact;//接触
}
