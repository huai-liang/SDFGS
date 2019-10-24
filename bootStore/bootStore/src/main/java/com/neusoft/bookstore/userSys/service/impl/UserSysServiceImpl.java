package com.neusoft.bookstore.userSys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.bookstore.base.PageResult;
import com.neusoft.bookstore.userSys.mapper.UserSysMapper;
import com.neusoft.bookstore.userSys.model.UserSys;
import com.neusoft.bookstore.userSys.service.UserSysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserSysServiceImpl implements UserSysService {
    @Autowired
    private UserSysMapper userSysMapper;

    @Override
    public void saveUserSysList(List<UserSys> userSys) {
        Map<String,Object> map = new HashMap<>();
        map.put("userSys",userSys);
        userSysMapper.saveUserSysList(map);
    }

    @Override
    public int deleteUserSys(List<String> list) {
        int count = userSysMapper.deleteUserSys(list);
        return count;
    }

    @Override
    public List<UserSys> getUserByName(List<String> list) {
        return userSysMapper.getUserByName(list);
    }

    /**
     * 分页查询
     * @return
     */
    @Override
    public PageResult listSysUserByPage(PageResult pageResult) {
        if (null != pageResult.getPageNum()&&null !=pageResult.getPageNum()){
            //总条数
            int count=userSysMapper.listUserCount();
            //总条数
            pageResult.setTotalCount(count);
            //总条数
            int totalPage=count % pageResult.getPageSize() == 0 ? count / pageResult.getPageSize() : count / pageResult.getPageSize()+1;
            pageResult.setTotalPage(totalPage);
            //起始数据
            int startData = (pageResult.getPageNum()-1)*pageResult.getPageSize();
            pageResult.setStartData(startData);
            List<UserSys> list = userSysMapper.listSysUserByPage(pageResult);
            //每页的数据
            pageResult.setDataList(list);
            return pageResult;
        }
        return null;
    }

    /**
     * 分页插件
     * @param pageNum  当前页数
     * @param pageSize  每页多少条
     */
    @Override
    public PageInfo<UserSys> listUserPageInfo(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<UserSys> userList=userSysMapper.listUserPageInfo();
        return new PageInfo<UserSys>(userList);
    }
}
