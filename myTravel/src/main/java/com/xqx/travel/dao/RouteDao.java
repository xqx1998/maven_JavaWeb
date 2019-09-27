package com.xqx.travel.dao;

import com.xqx.travel.domain.Route;

import java.util.List;

/**
 * @author： xingquanxiang
 * createTime：2019/9/23 15:18
 * description:
 */
public interface RouteDao {
    /**
     * 查询某一类路线总记录数
     * @param cid
     * @return
     */
    int countQuery(int cid, String rname);

    /**
     * 分页查询某一类旅游路线总记录数
     * @param cid
     * @param begin
     * @param pageSize
     * @return
     */
    List<Route> pageQuery(int cid, int begin, int pageSize, String rname);

    /**
     * 根据路线id查询路由线路信息
     * @param rid
     * @return
     */
    Route findOne(int rid);

    /**
     * 根据rid更新路线收藏次数
     * @param rid
     * @return
     */
    int updateRouteCountById(int rid);
}
