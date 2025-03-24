package com.example.federatedlearning.service;

import com.example.federatedlearning.mapper.UserMapper;
import com.example.federatedlearning.model.User;
import lombok.NonNull;

import java.time.LocalDateTime;

public interface UserService {

    //根据用户名查询用户
    User findByUserName(String username);

    //注册
    void register(String username, String password, /*String password_2,*/ String location, String status, LocalDateTime registration_time, String type, String role, String contact);

    // 更新用户状态
    boolean updateStatusByUserId(Long userId, String status);

    //获取用户状态
    String getUserStatus(Long userId);

    /*//更新
    void update(User user);*/

    /*
    //更新头像
    void updateAvatar(String avatarUrl);
     */

    /*//更新密码
    void updatePwd(String newPwd);*/

    //登出
    void setUserStatusOffline(Long userId);
}
