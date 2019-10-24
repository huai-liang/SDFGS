package com.neusoft.bookstore.userSys.model;

import lombok.Data;

@Data
public class UserSys {
    private Long id;
    private String user_name;
    private String user_acct;
    private String user_password;
    private String user_email;
    private String create_at;
}
