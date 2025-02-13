package com.example.federatedlearning.mapper;

import com.example.federatedlearning.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {
    //根据用户名查询用户
    @Select("select * from data_owners where name=#{name}")
    User findByUserName(String username);

    //添加
    @Insert("insert into data_owners(name,password,location,status,registration_name,type,role,contact)" +
            " values(#{name},#{password},#{location},'active',#{registration_name},#{type},#{role},#{contact})")
    void add(String name,String password,String location,String status,String registration_name,String type,String role,String contact);


}
//id
//名字
//位置
//状态 ENUM('active', 'offline') NOT NULL，
//登记的名字
//类型 ENUM（‘企业’，‘机构’）NOT NULL，
//角色
//接触
