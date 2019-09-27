package com.xqx.travel.dao.impl;

import com.xqx.travel.dao.FavoriteDao;
import com.xqx.travel.domain.Favorite;
import com.xqx.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;

/**
 * @author：xingquanxiang createTime：2019/9/24 21:49
 * description:
 */
public class FavoriteDaoImpl implements FavoriteDao {
    /**
     * 创建jdbcTemplate查询工具对象
     */
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 根据路线id查询收藏次数
     * @param rid
     * @return
     */
    @Override
    public Integer findCountByRid(int rid) {
        Integer count = 0;
        try {
            count = jdbcTemplate.queryForObject("select count(*) from tab_favorite where rid=?",
                    Integer.class, rid);
        } catch (DataAccessException e) {
            System.out.println("rid为"+rid+"旅游路线暂无收藏次数");
        }
        return count;
    }

    /**
     * 查询有收藏该路线的的用户
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite isFavorite(int rid, int uid) {
        Favorite favorite = null;
        try {
            favorite = jdbcTemplate.queryForObject("select * from tab_favorite where rid = ? and uid = ?",
                    new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            System.out.println("为查询到有收藏该路线的的用户");
        }
        return favorite;
    }
    /**
     * 添加收藏
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public int add(int rid, int uid) {
        int rows = 0;
        String dateTime = "";
        //获取当前系统时间
        dateTime = dateFormat.format(System.currentTimeMillis());
        System.out.println("dateTime = " + dateTime);
        //执行sql语句
        rows = jdbcTemplate.update("insert into tab_favorite(rid,date,uid) values(?,?,?)", rid, dateTime, uid);
        //返回影响行数
        return rows;
    }
}
