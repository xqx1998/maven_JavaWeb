package com.xqx.travel.dao;

import com.xqx.travel.domain.RouteImg;

import java.util.List;

/**
 * @author： xingquanxiang
 * createTime：2019/9/24 15:39
 * description:
 */
public interface RouteImgDao {
    /**
     * 根据rid线路id查询
     * @param rid
     * @return
     */
    List<RouteImg> findByRid(int rid);
}
