package com.neusoft.bookstore.base;

import lombok.Data;

@Data
public class PageRequest {
    //当前页数
    private Integer pageNum;
    //每页多少条
    private Integer pageSize;
}
