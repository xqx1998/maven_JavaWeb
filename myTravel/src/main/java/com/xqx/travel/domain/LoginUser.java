package com.xqx.travel.domain;

/**
 * @author：xingquanxiang createTime：2019/9/22 15:43
 * description:
 */
public class LoginUser {
    private String username;    // 用户名，账号
    private String password;    // 密码

    public LoginUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
