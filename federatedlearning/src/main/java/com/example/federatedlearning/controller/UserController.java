/*
package com.example.federatedlearning.controller;

import ch.qos.logback.core.util.MD5Util;
import com.example.federatedlearning.dto.UserLoginRequest;
import com.example.federatedlearning.dto.UserRegisterRequest;
import com.example.federatedlearning.service.UserService;
import com.example.federatedlearning.model.User;
import com.example.federatedlearning.pojo.Result;
import com.example.federatedlearning.utils.JwtUtil;
import com.example.federatedlearning.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;
    //注册
    @PostMapping("/register")
    public Result register(@Validated @RequestBody UserRegisterRequest request) {
        // 查询用户
        User u = userService.findByUserName(request.getUsername());
        if (u == null) {
            // 没有占用，注册
            userService.register(request.getUsername(), request.getPassword(),
                    request.getLocation(), request.getStatus(),
                    request.getRegistrationName(), request.getType(),
                    request.getRole(), request.getContact());
            return Result.success();
        } else {
            return Result.error("用户名已被占用");
        }
    }
    //登录

   // @RequestMapping("/login")
    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody UserLoginRequest request) {
        User loginUser = userService.findByUserName(request.getUsername());
        if (loginUser == null) {
            return Result.error("用户名错误");
        }
        if(Md5Util.md5(request.getPassword()).equals(loginUser.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getName());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }
}
*/
package com.example.federatedlearning.controller;

import com.example.federatedlearning.dto.UserLoginRequest;
import com.example.federatedlearning.dto.UserRegisterRequest;
import com.example.federatedlearning.service.UserService;
import com.example.federatedlearning.model.User;
import com.example.federatedlearning.pojo.Result;
import com.example.federatedlearning.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    // 注册
    @PostMapping("/register")
    public Result register(@Valid @RequestBody UserRegisterRequest request) {
        // 查询用户
        User u = userService.findByUserName(request.getUsername());
        if (u == null) {
            // 没有占用，注册
            userService.register(request.getUsername(), request.getPassword(),
                    request.getLocation(), request.getStatus(),
                    request.getRegistrationName(), request.getType(),
                    request.getRole(), request.getContact());
            return Result.success();
        } else {
            return Result.error("用户名已被占用");
        }
    }

    // 登录
    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody UserLoginRequest request) {
        try {
            // 使用 Spring Security 进行身份验证
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // 加载用户详细信息
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", ((User) userDetails).getId()); // 假设 UserDetails 实现中有 getId 方法
            claims.put("username", userDetails.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        } catch (Exception e) {
            return Result.error("用户名或密码错误");
        }
    }
}