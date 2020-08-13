package com.ming.service;

import com.ming.pojo.User;

public interface UserService {

    boolean isUserExist(Long userId);

    User addUser(User user);

    void deleteUser(Long userId);

    void updateUser(User user);

    User findUserByUsername(String username);

    User findUserById(Long id);


}
