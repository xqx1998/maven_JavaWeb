package com.xqx.travel.service.impl;


import com.xqx.travel.dao.CategoryDao;
import com.xqx.travel.dao.impl.CategoryDaoImpl;
import com.xqx.travel.domain.Category;
import com.xqx.travel.service.CategoryService;
import com.xqx.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author：xingquanxiang createTime：2019/9/22 19:57
 * description:
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    /**
     * 查询所有路线分类
     * @return
     */
    @Override
    public List<Category> findAll() {
        // 1.从redis中查询
        Set<Tuple> category = null;
        Jedis jedis = null;
        // 1.2.可使用sortedset排序查询
        try {
            // 1.1.获取redis客户端
            jedis = JedisUtil.getJedis();
            // category = jedis.zrange("category", 0, -1);
            category = jedis.zrangeWithScores("category", 0, -1);
        } catch (Exception e) {
            System.out.println("查询缓存失败，请检查redis服务是否开启或配置正确");
        }
        List<Category> categories = null;
        // 2.判断查询的集合是否为空
        if (category == null || category.size() == 0) {
            // 3.为空，则从数据库查询，将数据存入redis
            System.out.println("从数据库查询。。。。。");
            // 3.1.从数据库查询
            categories = categoryDao.findAll();
            // 3.2.将集合数据存储到redis中的 category 的key
            if (jedis!=null) {
                for (Category c : categories) {
                    jedis.zadd("category", c.getCid(), c.getCname());
                }
            }
        } else {
            System.out.println("从redis缓存中查询。。。。。");
            // 4.如果不为空，将set的数据存入list
            categories = new ArrayList<Category>();
            for (Tuple tuple : category) {
                Category c = new Category();
                c.setCid((int)tuple.getScore());
                c.setCname(tuple.getElement());
                categories.add(c);
            }
        }
        return categories;
    }
}
