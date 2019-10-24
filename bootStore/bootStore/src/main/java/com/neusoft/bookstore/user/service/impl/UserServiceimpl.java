package com.neusoft.bookstore.user.service.impl;

import com.neusoft.bookstore.user.mapper.UserMapper;
import com.neusoft.bookstore.user.model.Dept;
import com.neusoft.bookstore.user.model.User;
import com.neusoft.bookstore.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 新增用户
     * @param user
     */
    @Override
    public void saveUser(User user) {
        userMapper.saveUser(user);
    }
    /**
     * 修改用户
     * @param user
     */
    @Override
    public void updateUser(User user) {
        int count = userMapper.updateUser(user);
    }
    /**
     * 删除
     * @param user
     */
    @Override
    public void deleteUser(User user) {
        userMapper.deleteUser(user);
    }
    /**
     * 查询用户列表
     * @return
     */
    @Override
    public List<User> listUser(User user) {
        List<User> listUser = userMapper.listUser(user);
        return listUser;
    }
    /**
     * 根据id查询用户信息
     **/
    @Override
    public List<User> getUserById(List<Long> list) {
        return userMapper.getUserById(list);
    }
    /**
     * 批量新增用户
     **/
    @Override
    public void saveUserList(List<User> users) {
        Map<String,Object> map = new HashMap<>();
        map.put("users",users);
        userMapper.saveUserList(map);
    }
    /**
     *多条删除
     **/
    @Override
    public int deleteUsers(List<Long> list) {
        int count = userMapper.deleteUsers(list);
        return count;
    }

    /**
     * 多对一
     * @return
     */
    @Override
    public List<User> listManyToOne() {
        return userMapper.listManyToOne();
    }
    /**
     * 一对多
     * @return
     */
    @Override
    public List<Dept> listOneToMany() {
        return userMapper.listOneToMany();
    }
}
