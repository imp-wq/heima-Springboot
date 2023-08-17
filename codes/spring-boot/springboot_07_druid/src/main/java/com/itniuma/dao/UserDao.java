package com.itniuma.dao;

import com.itniuma.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {
    @Select("select * from mybatis.tb_user where id=#{id}")
    User getById(Integer id);
}
