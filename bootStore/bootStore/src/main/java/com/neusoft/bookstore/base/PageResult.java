package com.neusoft.bookstore.base;

import lombok.Data;

import java.util.List;

@Data
public class PageResult {
    //当前页数
    private Integer pageNum;
    //每页多少条
    private Integer pageSize;
    //总条数
    private Integer totalCount;
    //总页数
    private Integer totalPage;
    //每一页的数据
    private List<?> dataList;
    //起始数据
    private Integer startData;
}
