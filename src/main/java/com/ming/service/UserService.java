package com.ming.service;

import com.ming.pojo.User;

public interface UserService {

    User addUser(User user);

    User findUserByUsername(String username);

    User findUserById(Integer id);

    void updateUser(User user);
}
