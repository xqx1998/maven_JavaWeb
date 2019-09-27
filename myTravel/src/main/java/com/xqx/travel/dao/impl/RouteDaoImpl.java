package com.xqx.travel.dao.impl;

import com.xqx.travel.dao.RouteDao;
import com.xqx.travel.domain.Route;
import com.xqx.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：xingquanxiang createTime：2019/9/23 15:19
 * description:
 */
public class RouteDaoImpl implements RouteDao {
    /**
     * 创建jdbcTemplate查询工具对象
     */
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询某一类路线总记录数
     *
     * @param cid
     * @return
     */
    @Override
    public int countQuery(int cid, String rname) {
        //记录总数 默认为0
        int count = 0;
        //创建查询模板
        String sql = "select count(*) from tab_route where 1=1";
        /**
         * 创建StringBuild对象存储sql 方便拼接
         */
        StringBuilder sb = new StringBuilder(sql);
        //创建集合存储条件参数
        ArrayList params = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ?");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like ?");
            params.add("%" + rname + "%");
        }
        //将拼接好的查询语句赋给sql
        sql = sb.toString();
        System.out.println("count:");
        params.forEach(s-> System.out.println(s));
        //执行查询
        try {
            count = jdbcTemplate.queryForObject(sql,
                    Integer.class, params.toArray());
        } catch (DataAccessException e) {
            System.out.println("路线分类" + cid + "查询总记录数为0");
        }
        return count;

    }

    /**
     * 分页查询某一类旅游路线总记录数
     *
     * @param cid
     * @param begin
     * @param pageSize
     * @return
     */
    @Override
    public List<Route> pageQuery(int cid, int begin, int pageSize, String rname) {
        //创建list集合存储总数据库中查询的数据
        List<Route> routes = null;
        //创建查询模板
        String sql = "select * from tab_route where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        //创建集合存储条件参数
        ArrayList params = new ArrayList();
        //分类id处理
        if (cid != 0) {
            sb.append(" and cid = ?");
            params.add(cid);
        }
        //模糊名称查询
        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like ?");
            params.add("%" + rname + "%");
        }
        //查询记录条数
        sb.append(" limit ?,?");
        params.add(begin);
        params.add(pageSize);
        //将拼接好的查询语句赋给sql
        sql = sb.toString();
        System.out.println("data:");
        params.forEach(s-> System.out.println(s));
        //执行查询
        try {
            routes = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        } catch (DataAccessException e) {
            System.out.println("路线分类" + cid + "查询数据为空");
            e.printStackTrace();
        }
        return routes;
    }

    /**
     * 根据路线id查询路由线路信息
     *
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        Route route = null;
        try {
            route = jdbcTemplate.queryForObject("select * from tab_route where rid=?",
                    new BeanPropertyRowMapper<Route>(Route.class), rid);
        } catch (DataAccessException e) {
            System.out.println("查询路线id为"+rid+"的信息失败");
            // e.printStackTrace();
        }
        return route;
    }

    /**
     * 根据rid更新路线收藏次数
     * @param rid
     * @return
     */
    @Override
    public int updateRouteCountById(int rid) {
        return jdbcTemplate.update("update tab_route set count=count+1 where rid=?",rid);
    }
}
