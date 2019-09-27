package com.xqx.travel.dao.impl;

import com.xqx.travel.dao.RouteImgDao;
import com.xqx.travel.domain.RouteImg;
import com.xqx.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author：xingquanxiang createTime：2019/9/24 15:40
 * description:
 */
public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据rid线路id查询
     * @param rid
     * @return
     */
    @Override
    public List<RouteImg> findByRid(int rid) {
        List<RouteImg> imgs = null;
        try {
            imgs = jdbcTemplate.query("select * from tab_route_img where rid=?",
                    new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        } catch (DataAccessException e) {
            System.out.println("旅游路线id为"+rid+"的图片查询失败");
            // e.printStackTrace();
        }
        return imgs;
    }
}
