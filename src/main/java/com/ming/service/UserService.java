package com.ming.service;

import com.ming.pojo.User;

public interface UserService {

    User addUser(User user);

    User findUserByUsername(String username);

    User findUserById(Long id);

    void updateUser(User user);
}
