package com.example.federatedlearning.service;

import com.example.federatedlearning.model.User;

public interface UserService {
    //根据用户名查询用户
    User findByUserName(String username);

    void register(String username,String password,String location,String status,String registration_name,String type,String role,String contact);


}
