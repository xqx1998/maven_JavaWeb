package com.xqx.travel.service;

import com.xqx.travel.domain.Category;

import java.util.List;

/**
 * @author： xingquanxiang
 * createTime：2019/9/22 19:57
 * description:
 */
public interface CategoryService {
    /**
     * 查询所有路线分类
     * @return
     */
    List<Category> findAll();
}
