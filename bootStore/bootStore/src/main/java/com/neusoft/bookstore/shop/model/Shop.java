package com.neusoft.bookstore.shop.model;

import lombok.Data;

import java.util.Date;

@Data
public class Shop {
    private String token;
    private Long id;
    private Long skuId;
    private Integer count;
    private Integer isDeleted;
    private Integer sortNo;
    private Date gmtCreate;
    private String createBy;
    private Date gmtModified;
    private String lastModifiedBy;
    private Integer version;
}
