package com.geektime.ssjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Lazy
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("all1")
    public List<User> getAllUser1() {
        return userMapper.findAll();
    }

    @GetMapping("all2")
    public List<User> getAllUser2() {
        return userMapper.findAll();
    }
}
