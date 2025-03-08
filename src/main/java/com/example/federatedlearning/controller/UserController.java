package com.example.federatedlearning.controller;


import com.example.federatedlearning.dto.UserLoginRequest;
import com.example.federatedlearning.dto.UserRegisterRequest;
import com.example.federatedlearning.service.UserService;
import com.example.federatedlearning.model.User;
import com.example.federatedlearning.pojo.Result;
import com.example.federatedlearning.utils.JwtUtil;
import com.example.federatedlearning.utils.Md5Util;
import com.example.federatedlearning.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController //表示这是一个控制器，返回 JSON 格式的响应。
@RequestMapping("/user") //类级别的请求映射，所有请求的前缀为 /user。
@Validated //启用参数验证。
public class UserController {

    @Autowired
    private UserService userService;

    //注册
    @PostMapping("/register")
    public Result register(@Validated @RequestBody UserRegisterRequest request) {
        User u = userService.findByUserName(request.getUsername());// 查询用户
        if (u == null) {// 没有占用，注册
            userService.register(request.getUsername(), request.getPassword(),request.getPassword_2(),
                    request.getLocation(), "offline",
                    LocalDateTime.now(), request.getType(),
                    request.getRole(), request.getContact());
            return Result.success();
        } else {
            return Result.error("用户名已被占用");
        }
    }

    /*//登录
    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody UserLoginRequest request) {
        User loginUser = userService.findByUserName(request.getUsername());
        if (loginUser == null) {
            return Result.error("用户名错误");
        }
        if (Md5Util.md5(request.getPassword()).equals(loginUser.getPassword())) {
            // 登录成功，更新 data_owners 中的 status 为 active
            boolean updateStatus = userService.updateStatusByUserId((long) loginUser.getId(), "active"); // 确保通过实例调用
            if (!updateStatus) {
                return Result.error("更新状态失败");
            }

            // 登录成功，存储用户信息到 ThreadLocal
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", loginUser.getName());
            ThreadLocalUtil.set(userInfo); // 设置 ThreadLocal

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getName());

            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Result.error("密码错误"));
    }*/
    // 登录
    @PostMapping("/login")
    public ResponseEntity<Result<String>> login(@Validated @RequestBody UserLoginRequest request) {
        User loginUser = userService.findByUserName(request.getUsername());

        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Result.error("用户名错误"));
        }

        if (Md5Util.md5(request.getPassword()).equals(loginUser.getPassword())) {
            // 登录成功，更新 data_owners 中的 status 为 active
            boolean updateStatus = userService.updateStatusByUserId((long) loginUser.getId(), "active");
            if (!updateStatus) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.error("更新状态失败"));
            }

            /*
            // 登录成功，存储用户信息到 ThreadLocal
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", loginUser.getName());
            ThreadLocalUtil.set(userInfo); // 设置 ThreadLocal*/

            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getName());

            String token = JwtUtil.genToken(claims);
            System.out.println(token);

            // 返回成功的 token
            return ResponseEntity.ok(Result.success(token)); // 返回 token 和 200 状态
        }

        // 密码错误的情况
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Result.error("密码错误")); // 返回 401 状态
    }
    //登出
    @PostMapping("/logout")
    public Result<String> logout(@Validated @RequestBody UserLoginRequest request) {
        // 从请求中获取用户 ID，假设 UserLoginRequest 中有 userId 字段
        User loginUser = userService.findByUserName(request.getUsername());

        // 调用服务方法设置用户状态为 offline
        userService.updateStatusByUserId((long) loginUser.getId(), "offline");
        ThreadLocalUtil.remove();

        // 返回成功的结果
        return Result.success("Logout successful");

    }


    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization") String token){
        //根据用户名查询用户
        Map<String, Object> map = JwtUtil.parseToken(token);
        String username = (String) map.get("username");

        /*Map<String,Object> map = ThreadLocalUtil.get();
        if (map == null || !map.containsKey("username")) {
            return Result.error("未找到用户信息");
        }*/
        //String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

/*    //更新
    @PutMapping("/update" )
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }*/

   /* //更新头像 @url校验已传递的地址是不是url地址
    @PatchMapping("updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }*/

/*    //更新密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        //1.校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) ||!StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }
        // 获取 ThreadLocal 中的用户信息
        Map<String, Object> map = ThreadLocalUtil.get();
        if (map == null ) {
            return Result.error("未找到用户信息1");
        }
        if (!map.containsKey("username")) {
            return Result.error("未找到用户信息2");
        }
        //校验原密码是否正确
        //调用userservice，根据用户名拿到原密码和oldpwd比对
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);
        if (!loginUser.getPassword().equals(Md5Util.md5(oldPwd))){
            return Result.error("原密码填写不正确");
        }

        //检验newpwd和repwd是否一样
        if (!rePwd.equals(newPwd)){
            return Result.error("两次填写的新密码不一致");
        }

        //2.调用service完成密码更新
        userService.updatePwd(newPwd);
        return Result.success();

    }*/
}
