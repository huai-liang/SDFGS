package com.neusoft.bookstore.userSys.mapper;

import com.neusoft.bookstore.base.PageResult;
import com.neusoft.bookstore.userSys.model.UserSys;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserSysMapper {
    /**
     * 多条新增
     **/
    void saveUserSysList(Map<String,Object> userSys);
    /**
     * 删除
     **/
    int deleteUserSys(List<String> list);

    /**
     * 根据用户name查询用户信息
     **/
    List<UserSys> getUserByName(List<String> list);

    /**
     * 分页查询用户信息
     **/
    List<UserSys> listSysUserByPage(PageResult pageResult);

    int listUserCount();

    /**
     * 分页插件
     * @return
     */
    List<UserSys> listUserPageInfo();
}
