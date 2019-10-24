package com.neusoft.bookstore.test;

import com.neusoft.bookstore.base.BookStoreApplicationTests;
import com.neusoft.bookstore.user.model.Dept;
import com.neusoft.bookstore.user.model.User;
import com.neusoft.bookstore.user.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserTest extends BookStoreApplicationTests {

    @Autowired
    private UserService userService;

    /**
     * 测试新增用户
     */
    @Test
    public void saveUserTest(){
        User user = new User();
        user.setUserName("张三");
        user.setEmail("123@qq.com");
        user.setAddress("宁波");
        userService.saveUser(user);
    }
    /**
     * 修改
     */
    @Test
    public void updateUserTest(){
        User user = new User();
        user.setId(7L);
        user.setUserName("李四");
        user.setEmail("456@qq.com");
        user.setAddress("上海");
        userService.updateUser(user);
    }
    /**
     * 删除
     */
    @Test
    public void deleteUserTest(){
        User user = new User();
        user.setId(8L);
        userService.deleteUser(user);
    }
    /**
     * 查询用户列表
     */
    @Test
    public void listUserTest(){
        User user = new User();
        user.setUserName("张三");
        List<User> listUser = userService.listUser(user);
//        for (int i=1; i<listUser.size();i++){
//            User user1=listUser.get(i);
//            System.out.println(user1);
//        }
        for (User user1: listUser){
            System.out.println(user1);
        }
//        listUser.forEach(user -> {
//            System.out.println(user);
//        });
    }
    /**
     * 查询多条id的用户信息
     **/
    @Test
    public void getUserByIdTest(){
        List<Long> list = new ArrayList<>();
        list.add(7L);
        list.add(8L);
        list.add(9L);
        List<User> userList= userService.getUserById(list);
        userList.forEach(user -> {
            System.out.println(user);
        });
    }
    /**
     * 批量保存
     **/
    @Test
    public void saveUserListTest(){
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setUserName("段誉");
        user1.setEmail("duanyu@qq.com");
        user1.setAddress("大理");
        users.add(user1);
        User user2 = new User();
        user2.setUserName("王姑娘");
        user2.setEmail("wgn@qq.com");
        user2.setAddress("大理");
        users.add(user2);
        userService.saveUserList(users);
    }
    /**
     * 批量删除
     **/
    @Test
    public void deleteUsersTest(){
        List<Long> list = new ArrayList<>();
        list.add(7L);
        list.add(8L);
        list.add(9L);
        int i = userService.deleteUsers(list);
    }
    /**
     * 多对一
     **/
    @Test
    public void listManyToOneTest(){
        List<User> users = userService.listManyToOne();
        users.forEach(user -> {
            System.out.println(user);
        });
    }
    /**
     * 一对多
     **/
    @Test
    public void listOneToManyTest(){
        List<Dept> depts = userService.listOneToMany();
        depts.forEach(dept -> {
            System.out.println(dept);
        });
    }
}
