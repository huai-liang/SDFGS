package com.neusoft.bookstore.userSys.service;

import com.github.pagehelper.PageInfo;
import com.neusoft.bookstore.base.PageResult;
import com.neusoft.bookstore.userSys.model.UserSys;

import java.util.List;

public interface UserSysService {
    /**
     * 批量新增用户
     **/
    void saveUserSysList(List<UserSys> userSys);

    /**
     * 多条删除
     **/
    int deleteUserSys(List<String> list);
    /**
     * 根据id批量查询用户信息
     **/
    List<UserSys> getUserByName(List<String> list);

    /**
     *分页查询
     */
    PageResult listSysUserByPage(PageResult pageResult);

    /**
     * 分页插件查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<UserSys> listUserPageInfo(int pageNum, int pageSize);
}
