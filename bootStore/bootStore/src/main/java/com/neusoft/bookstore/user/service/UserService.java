package com.neusoft.bookstore.user.service;

import com.neusoft.bookstore.user.model.Dept;
import com.neusoft.bookstore.user.model.User;

import java.util.List;

public interface UserService {
    /**
     * 新增用户
     */
    void saveUser(User user);
    /**
     * 修改用户
     */
    void updateUser(User user);
    /**
     * 删除用户
     */
    void deleteUser(User user);
    /**
     * 查询用户列表
     */
    List<User> listUser(User user);
    /**
     * 根据id批量查询用户信息
     **/
    List<User> getUserById(List<Long> list);
    /**
     * 批量新增用户
     **/
    void saveUserList(List<User> users);
    /**
     * 多条删除
     **/
    int deleteUsers(List<Long> list);
    /**
     * 多对一
     * @return
     */
    List<User> listManyToOne();

    /**
     * 一对多
     * @return
     */
    List<Dept> listOneToMany();
}
