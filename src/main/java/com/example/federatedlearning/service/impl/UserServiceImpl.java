package com.example.federatedlearning.service.impl;

import com.example.federatedlearning.mapper.UserMapper;
import com.example.federatedlearning.model.User;
import com.example.federatedlearning.service.UserService;
import com.example.federatedlearning.utils.Md5Util;
import com.example.federatedlearning.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username){
        User u=userMapper.findByUserName(username);
        return u;
    }


    @Override
    public void register(String username, String password, String password_2, String location, String status,
                         LocalDateTime registrationName, String type, String role, String contact) {
        if(password.equals(password_2)){
            // 加密密码
            String md5String = Md5Util.md5(password);

            // 调用 Mapper 方法添加用户信息
            userMapper.add(username, md5String, location, status, registrationName, type, role, contact);
        }
        else{
            throw new IllegalArgumentException("两次输入的密码不匹配");
        }
    }

    @Override
    @Transactional
    public boolean updateStatusByUserId(Long userId, String status) {
        if (userId == null || status == null || status.isEmpty()) {
            throw new IllegalArgumentException("User ID and status cannot be null or empty");
        }
        int updatedRows = userMapper.updateStatusByUserId(userId.intValue(), status);
        logger.info("更新用户状态：用户ID = {}, 状态 = {}, 更新行数 = {}", userId, status, updatedRows);

        return updatedRows > 0;
    }

    public Optional<User> getUserById(long userId) {
        User user = userMapper.findById(userId); // 查询用户
        return Optional.ofNullable(user); // 包装为 Optional<User>
    }

    @Override
    @Transactional
    public void setUserStatusOffline(Long userId) {
        Optional<User> user = getUserById(userId);
        if (user.isPresent()) {
            User currentUser = user.get();
            currentUser.setStatus("offline"); // 假设你有一个 status 字段
            updateStatusByUserId(userId, currentUser.getStatus());
        }
    }

    @Override
    public String getUserStatus(Long userId) {
        Optional<User> user = getUserById(userId);
        return user.map(User::getStatus).orElse(null); // 返回用户状态，若用户不存在则返回 null
    }

    /*
    //更新
    @Override
    public void update(User user){
        userMapper.update(user);
    }*/

    /*
    //更新头像
    @Override
    public void updateAvatar(String avatarUrl){
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }*/

    /*
    //更新密码
    @Override
    @Transactional
    public void updatePwd(String newPwd){
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.md5(newPwd),id);
    }*/
}
