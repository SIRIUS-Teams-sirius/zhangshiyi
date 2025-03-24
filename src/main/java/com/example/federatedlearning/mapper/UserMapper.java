package com.example.federatedlearning.mapper;

import com.example.federatedlearning.model.User;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;


@Mapper
public interface UserMapper {
    //根据用户名查询用户
    @Select("select * from data_owners where name=#{name}")
    User findByUserName(String username);

    //根据id查询用户
    @Select("select * from data_owners where id=#{id}")
    User findById(long id);

    //添加用户
    @Insert("insert into data_owners(name,password,location,status,registration_time,type,role,contact)" +
            " values(#{name},#{password},#{location},'offline',now(),#{type},#{role},#{contact})")
    void add(String name, String password, String location, String status, LocalDateTime registration_time, String type, String role, String contact);

    // 更新用户状态为 active
    @Update("UPDATE data_owners SET status=#{status} WHERE id=#{id}")
    int updateStatusByUserId(@Param("id") Integer id, @Param("status") String status);

   /* //更新
    @Update("update data_owners set name=#{name},location=#{location},status=#{status},registration_name=now(),type=#{type},role=#{role},contact=#{contact} where id=#{id}")
    void update(User user);*/

    /*//更新头像
    @Update("update user set user_pic=#{avatarUrl} where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);
     */

    /*//更新密码
    @Update("update data_owners set password=#{md5String} where id=#{id}")
    void updatePwd(String md5String,Integer id);*/
}
//id
//名字
//位置
//状态 ENUM('active', 'offline') NOT NULL，
//登记的名字
//类型 ENUM（‘企业’，‘机构’）NOT NULL，
//角色
//接触
