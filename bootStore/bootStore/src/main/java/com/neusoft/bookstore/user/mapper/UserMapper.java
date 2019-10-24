package com.neusoft.bookstore.user.mapper;

import com.neusoft.bookstore.user.model.Dept;
import com.neusoft.bookstore.user.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    /**
     * 新增用户
     */
    void saveUser(User user);
    /**
     * 修改用户
     * @param user
     * @return
     */
    int updateUser(User user);
    /**
     * 删除用户
     * @param user
     * @return
     */
    void deleteUser(User user);
    /**
     * 查询用户列表
     * @return
     */
    List<User> listUser(User user);
    /**
     * 根据用户id查询用户信息
     **/
    List<User> getUserById(List<Long> list);
    /**
     * 多条新增
     **/
    void saveUserList(Map<String,Object> users);
    /**
     * 删除
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
