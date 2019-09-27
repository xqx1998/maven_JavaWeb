package com.xqx.travel.service;

import com.xqx.travel.domain.PageBean;
import com.xqx.travel.domain.Route;

/**
 * @author： xingquanxiang
 * createTime：2019/9/23 14:52
 * description:
 */
public interface RouteService {
    /**
     * 实现旅游路线分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 查询单个旅游路线详细信息
     * @param rid
     * @return
     */
    Route findOne(int rid);
}
