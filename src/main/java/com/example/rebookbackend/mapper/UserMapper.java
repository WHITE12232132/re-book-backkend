package com.example.rebookbackend.mapper;

import com.example.rebookbackend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 简单查询可以直接用注解，复杂的才写 XML
    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    User findByUsername(String username);
}
