package com.example.federatedlearning.service.impl;

import com.example.federatedlearning.mapper.UserMapper;
import com.example.federatedlearning.model.User;
import com.example.federatedlearning.service.UserService;
import com.example.federatedlearning.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username){
        User u=userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username,String password,String location,String status,String registration_name,String type,String role,String contact){
        String md5String= Md5Util.md5(password);
        userMapper.add(username,password,location,status,registration_name,type,role,contact);
    }
}
