package com.zhouq.eshop.inventory.service.impl;

import com.zhouq.eshop.inventory.mapper.UserMapper;
import com.zhouq.eshop.inventory.model.User;
import com.zhouq.eshop.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhouq on 2017/11/29.
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(int id) {

        return userMapper.findUserById(id);
    }
}
