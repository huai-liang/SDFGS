package com.neusoft.bookstore.user.model;

import lombok.Data;

/**
 *
 */
@Data
public class User {
    private Long id;
    private String userName;//用户名
    private String email;//邮箱
    private String address;//地址
    private Dept dept;//部门对象
}
