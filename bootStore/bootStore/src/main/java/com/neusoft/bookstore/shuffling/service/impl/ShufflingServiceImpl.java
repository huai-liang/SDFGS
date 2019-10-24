package com.neusoft.bookstore.shuffling.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.shuffling.mapper.ShufflingMapper;
import com.neusoft.bookstore.shuffling.model.Shuffling;
import com.neusoft.bookstore.shuffling.service.ShufflingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShufflingServiceImpl implements ShufflingService {
    @Autowired
    private ShufflingMapper shufflingMapper;

    /**
     * 分页查询轮播列表
     * @param shuffling
     * @return
     */
    @Override
    public Response listShuffling(Shuffling shuffling) {
        if (null == shuffling.getPageNum() || null == shuffling.getPageSize()){
            return Response.error("参数传递错误");
        }
        PageHelper.startPage(shuffling.getPageNum(),shuffling.getPageSize());
        List<Shuffling> list = shufflingMapper.listShuffling(shuffling);
        PageInfo<Shuffling> pageInfo = new PageInfo<>(list);
        return Response.ok(pageInfo,"查询成功");
    }
    /**
     * 新增轮播
     * @param shuffling
     * @return
     */
    @Override
    public Response saveShuffling(Shuffling shuffling) {
        int count = shufflingMapper.saveShuffling(shuffling);
        if (count<=0){
            return Response.error("新增失败");
        }
        return Response.ok("新增成功");
    }
    /**
     * 删除轮播
     * @param shuffling
     * @return
     */
    @Override
    public Response deleteShufflingById(Shuffling shuffling) {
        int count =shufflingMapper.deleteShufflingById(shuffling);
        if (count<=0){
            return Response.error("删除失败");
        }
        return Response.ok("删除成功");
    }
    /**
     * 轮播启用禁用
     * @param shuffling
     * @return
     */
    @Override
    public Response updShuffling(Shuffling shuffling) {
        int count = shufflingMapper.updShuffling(shuffling);
        if (count<=0){
            return Response.error("执行失败");
        }
        return Response.ok("执行成功");
    }
}
