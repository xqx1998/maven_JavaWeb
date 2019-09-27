package com.xqx.travel.service;

import com.xqx.travel.domain.User;

/**
 * @author： xingquanxiang
 * createTime：2019/9/21 14:23
 * description:
 */
public interface UserService {
    /**
     * 完成用户注册
     * @param user
     * @return true or false
     */
    String register(User user);

    /**
     * 邮箱验证激活账户
     * @param activeCode
     * @return
     */
    boolean active(String activeCode);

    /**
     * 完成用户登录
     * @param user
     * @return
     */
    User login(User user);
}
