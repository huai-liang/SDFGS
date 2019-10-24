package com.neusoft.bookstore.customer.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class Customer {
    @ApiModelProperty(value="id")
    private Long id;
    @ApiModelProperty(value="用户名称")
    private String userName;
    @ApiModelProperty(value="用户账号")
    private String userAcct;
    @ApiModelProperty(value="用户密码")
    private String userPwd;
    @ApiModelProperty(value="管理员标记")
    private Integer adminFlag;
    ////0客户 1用户
    private Integer userType;
    private String idCard;
    private Integer sex;
    private String phone;
    private String email;
    private String remark;
    private Integer score;
    private String clientId;
    private String createBy;
    private String lastModifiedBy;
    private Long verifyCode;
    private Integer deleted;
    private Integer sortNo;
    private String gmtCreate;
    private String gmtModified;
    private String version;

    private Integer pageNum;
    private Integer pageSize;

    private String token;

    private List<Long> idList;
}
