package com.example.demo.dao;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("Select * from user where username=#{username}")
    User findUserByUsername(@Param("username") String username);

    @Insert("insert into user(username,encrypted_password,created_at,updated_at) " +
            "values(#{username},#{encryptedPassword},now(),now())")
    void save(@Param("username") String username,@Param("encryptedPassword") String encryptedPassword);
    @Select("Select * from user where id=#{id}")
    User getUserById(@Param("id")Integer id);
//    @Insert("insert into blog(,encrypted_password,created_at,updated_at) " +
//            "values(#{username},#{encryptedPassword},now(),now())")
//    void saveBlog(@Param("username") String username,@Param("encryptedPassword") String encryptedPassword);
}
