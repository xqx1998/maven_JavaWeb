package com.xqx.travel.dao.impl;

import com.xqx.travel.dao.CategoryDao;
import com.xqx.travel.domain.Category;
import com.xqx.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author：xingquanxiang createTime：2019/9/22 20:09
 * description:
 */
public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 查询所有路线分类
     * @return
     */
    @Override
    public List<Category> findAll() {
        return jdbcTemplate.query("select * from tab_category",new BeanPropertyRowMapper<Category>(Category.class));
    }
}
