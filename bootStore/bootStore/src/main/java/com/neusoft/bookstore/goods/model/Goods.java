package com.neusoft.bookstore.goods.model;

import com.neusoft.bookstore.base.PageRequest;
import com.neusoft.bookstore.file.modle.File;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Goods extends PageRequest {
    private Integer flag;
    private Long id;
    private Integer goodsType;
    private String skuName;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private Integer saleCount;
    private Date putawayTime;
    private Date lowerTime;
    private Integer status;
    private Long browseCount;
    private String author;
    private String advertising;
    private String isbn;
    private Integer stock;
    private Integer isDeleted;
    private Integer sortNo;
    private Date gmtCreate;
    private String createBy;
    private Date gmtModified;
    private String lastModifiedBy;
    private Integer version;
    private Long oneCateId;
    private Long twoCateId;
    private String detail;

    private List<File> files;
}
