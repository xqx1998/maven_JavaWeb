package com.xqx.travel.service.impl;

import com.xqx.travel.dao.UserDao;
import com.xqx.travel.dao.impl.UserDaoImpl;
import com.xqx.travel.domain.Favorite;
import com.xqx.travel.domain.User;
import com.xqx.travel.service.UserService;
import com.xqx.travel.util.MailUtils;
import com.xqx.travel.util.UuidUtil;

/**
 * @author：xingquanxiang createTime：2019/9/21 14:25
 * description:
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    /**
     * 完成用户注册
     * @param user
     * @return true or false
     */
    @Override
    public String register(User user) {
        //根据用户名查询用户对象
        if (userDao.findByUsername(user.getUsername()) != null){
            //用户名存在，注册失败
            return null;
        }
        user.setStatus("N");
        String uuid = UuidUtil.getUuid();
        user.setCode(uuid);
        //保存用户信息
        userDao.register(user);
        return uuid;
    }

    /**
     * 邮箱验证激活账户
     * @param activeCode
     * @return
     */
    @Override
    public boolean active(String activeCode) {
        User user = userDao.findUserByActiveCode(activeCode);
        System.out.println(user);
        if(user==null){
            return false;
        }
        user.setStatus("Y");
        userDao.updateUserStatus(user);
        return true;
    }

    /**
     * 完成用户登录
     * @param user
     * @return 返回数据查询到用户信息
     */
    @Override
    public User login(User user) {
        user = userDao.login(user);
        //更新用户激活码
        if (user!=null){
            user.setCode(UuidUtil.getUuid());
            userDao.updateUserActiveCode(user);
        }
        return user;
    }
}
