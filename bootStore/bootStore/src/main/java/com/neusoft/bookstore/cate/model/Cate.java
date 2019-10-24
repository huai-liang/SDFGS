package com.neusoft.bookstore.cate.model;

import lombok.Data;

import java.util.Date;

@Data
public class Cate {
    private Long id;//分类id
    private String cateName;//分类名称
    private Long parentCateId;//上级分类id
    private Integer cateLevel;//层级（第一层1，后续2,3等等）
    private String remark;//备注
    private Integer isDeleted;//作废标记（1作废，0未作废）
    private Integer sortNo;//序号（从0开始）
    private Date gmtCreate;//创建时间
    private String createBy;//创建者(登录账号)
    private Date gmtModified;//更新时间
    private String lastModifiedBy;//更新者(登录账号)
    private Integer version;//版本号

    private String token;
}
