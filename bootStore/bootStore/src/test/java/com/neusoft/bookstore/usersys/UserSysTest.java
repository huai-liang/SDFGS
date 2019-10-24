package com.neusoft.bookstore.usersys;

import com.github.pagehelper.PageInfo;
import com.neusoft.bookstore.base.BookStoreApplicationTests;
import com.neusoft.bookstore.base.PageResult;
import com.neusoft.bookstore.user.model.User;
import com.neusoft.bookstore.userSys.model.UserSys;
import com.neusoft.bookstore.userSys.service.UserSysService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserSysTest  extends BookStoreApplicationTests {
    @Autowired
    private UserSysService userSysService;
    /**
     * 批量保存
     **/
    @Test
    public void saveUserListTest(){
        Date date=new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        System.out.println(strDate);
        List<UserSys> users = new ArrayList<>();
        UserSys user1 = new UserSys();
        user1.setUser_name("西门吹雪");
        user1.setUser_acct("ximen");
        user1.setUser_password("123456");
        user1.setUser_email("");
        user1.setCreate_at(strDate);
        users.add(user1);
        UserSys user2 = new UserSys();
        user2.setUser_name("郭靖");
        user2.setUser_acct("guojing");
        user2.setUser_password("gongjing123");
        user2.setUser_email("guojing@qq.com");
        user2.setCreate_at(strDate);
        users.add(user2);
        UserSys user3 = new UserSys();
        user3.setUser_name("黄蓉");
        user3.setUser_acct("huangrong");
        user3.setUser_password("h23478");
        user3.setUser_email("huangrong@qq.com");
        user3.setCreate_at(strDate);
        users.add(user3);
        UserSys user4 = new UserSys();
        user4.setUser_name("杨过");
        user4.setUser_acct("yangguo");
        user4.setUser_password("yg234789");
        user4.setUser_email("yangguo@qq.com");
        user4.setCreate_at(strDate);
        users.add(user4);
        UserSys user5 = new UserSys();
        user5.setUser_name("小龙女");
        user5.setUser_acct("xiaolongnv");
        user5.setUser_password("xl234789");
        user5.setUser_email("xiaolongnv@qq.com");
        user5.setCreate_at(strDate);
        users.add(user5);
        userSysService.saveUserSysList(users);
    }
    /**
     * 批量删除
     **/
    @Test
    public void deleteUsersTest(){
        List<String> list = new ArrayList<>();
//        list.add("小龙女");
//        list.add("郭靖");
        list.add("西门吹雪");
        list.add("黄蓉");
        list.add("杨过");
        int i = userSysService.deleteUserSys(list);
    }
    /**
     * 查询多条id的用户信息
     **/
    @Test
    public void getUserByNameTest(){
        List<String> list = new ArrayList<>();
        list.add("杨过");
        list.add("黄蓉");
        List<UserSys> userList= userSysService.getUserByName(list);
        userList.forEach(user -> {
            System.out.println(user);
        });
    }
    /**
     * 分页查询用户信息
     **/
    @Test
    public void listSysUserByPage(){
        PageResult pageResult=new PageResult();
        pageResult.setPageNum(1);//第多少页
        pageResult.setPageSize(3);//每页显示的多少条数据
        PageResult pageResult1=userSysService.listSysUserByPage(pageResult);
        System.out.println(pageResult1);
    }

    /**
     * 分页查询用户信息
     **/
    @Test
    public void listUserPageInfoTest(){
        PageInfo<UserSys> sysUserPageInfo = userSysService.listUserPageInfo(1,2);
        System.out.println(sysUserPageInfo);
    }
}
