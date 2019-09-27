package com.xqx.travel.dao;

import com.xqx.travel.domain.Category;

import java.util.List;

/**
 * @author： xingquanxiang
 * createTime：2019/9/22 20:09
 * description:
 */
public interface CategoryDao {
    /**
     * 查询所有路线分类
     * @return
     */
    List<Category> findAll();
}
