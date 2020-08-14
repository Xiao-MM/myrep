package com.ming.service;

import com.ming.dto.UserDTO;
import com.ming.pojo.User;

import java.util.List;

public interface UserService {

    boolean isUserExist(Long userId);

    /**
     * 添加用户
     * @param user
     * @return
     */
    User addUser(User user);

    /**
     * 删除用户
     * @param userId
     */
    void deleteUser(Long userId);

    /**
     * 修改用户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    User findUserById(Long id);

    /**
     * 获取所有用户
     * @return
     */
    List<User> findAllUsers();

}
