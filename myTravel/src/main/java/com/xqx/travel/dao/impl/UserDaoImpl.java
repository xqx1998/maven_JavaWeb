package com.xqx.travel.dao.impl;

import com.xqx.travel.dao.UserDao;
import com.xqx.travel.domain.User;
import com.xqx.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author：xingquanxiang createTime：2019/9/21 14:28
 * description:
 */
public class UserDaoImpl implements UserDao {
    JdbcTemplate  jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            user =  jdbcTemplate.queryForObject("select * from tab_user where username = ?",
                    new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {
            //查询数据库的结果为空，会发生异常，用try catch 捕获
            System.out.println("未查询到指定用户名的用户信息，可以注册！");
        }
        return user;
    }

    /**
     * 完成用户注册
     * @param user
     * @return 数据库影响行数
     */
    @Override
    public int register(User user) {
        return jdbcTemplate.update("insert into tab_user(username, password,name,birthday,sex,telephone,email,status,code) values (?,?,?,?,?,?,?,?,?)",
                user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(),
                user.getEmail(), user.getStatus(), user.getCode());
    }

    /**
     * 根据激活码查询用户信息
     * @param activeCode
     * @return
     */
    @Override
    public User findUserByActiveCode(String activeCode) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject("select * from tab_user where code=?", new BeanPropertyRowMapper<User>(User.class), activeCode);
        }catch (Exception e){
            System.out.println("未查询到激活码为"+activeCode+"的指定用户名的用户信息，激活失败！");
        }
        return user;
    }

    /**
     * 修改用户激活状态
     * @param user
     */
    @Override
    public void updateUserStatus(User user) {
        jdbcTemplate.update("update tab_user set status=? where uid=?",user.getStatus(),user.getUid());
    }

    /**
     * 根据用户名和密码查询用户信息
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        User dbUser = null;
        try {
            dbUser = jdbcTemplate.queryForObject("select * from tab_user where username=? and password=?",
                    new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
        } catch (DataAccessException e) {

        }
        return dbUser;
    }

    /**
     * 更新用户激活码
     * @param user
     */
    @Override
    public void updateUserActiveCode(User user) {
        jdbcTemplate.update("update tab_user set code=? where uid=?",user.getCode(),user.getUid());
    }
}
