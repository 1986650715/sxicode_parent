package com.sxicode.user.service;

import com.sxicode.user.dao.UserDao;
import com.sxicode.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User selectById(String userId){
        return userDao.selectById(userId);
    }
}
