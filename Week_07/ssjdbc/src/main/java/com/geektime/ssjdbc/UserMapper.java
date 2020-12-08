package com.geektime.ssjdbc;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper
@Lazy
public interface UserMapper {

    @Select("select * from t_user")
    List<User> findAll();

}
