package com.xqx.travel.dao.impl;

import com.xqx.travel.dao.SellerDao;
import com.xqx.travel.domain.Seller;
import com.xqx.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author：xingquanxiang createTime：2019/9/24 15:45
 * description:
 */
public class SellerDaoImpl implements SellerDao {
    /**
     * 创建jdbcTemplate查询工具对象
     */
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据sid查询卖家信息
     * @param sid
     * @return
     */
    @Override
    public Seller findOne(Integer sid) {
        Seller seller = null;
        try {
            seller = jdbcTemplate.queryForObject("select * from tab_seller where sid=?",
                    new BeanPropertyRowMapper<Seller>(Seller.class), sid);
        } catch (DataAccessException e) {
            System.out.println("查询卖家sid为"+sid+"信息失败");
            // e.printStackTrace();
        }
        return seller;
    }
}
