package com.xqx.travel.dao;

import com.xqx.travel.domain.User;

/**
 * @author： xingquanxiang
 * createTime：2019/9/21 14:27
 * description:
 */
public interface UserDao {
    /**
     * 根据用户名查询用户是否存在
     * @param username
     * @return
     */
    User findByUsername(String username);
    /**
     * 完成用户注册
     * @param user
     * @return 数据库影响行数
     */
    int register(User user);

    /**
     * 根据激活码查询用户信息
     * @param activeCode
     * @return
     */
    User findUserByActiveCode(String activeCode);

    /**
     * 修改用户激活状态
     * @param user
     */
    void updateUserStatus(User user);

    /**
     * 根据用户名和密码查询用户信息
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 更新用户激活码
     * @param user
     */
    void updateUserActiveCode(User user);

}
