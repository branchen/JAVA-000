package com.geektime.rwsepara01.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.geektime.rwsepara01.anno.WriteDateSourceSource;
import com.geektime.rwsepara01.domain.User;
import com.geektime.rwsepara01.mapper.UserMapper;
import org.springframework.stereotype.Service;


@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    User findByFirstDD(Long id) {
        return baseMapper.selectById(id);
    }

    @WriteDateSourceSource
    User findBuSecondDD(Long id) {
        return baseMapper.selectById(id);
    }
}
