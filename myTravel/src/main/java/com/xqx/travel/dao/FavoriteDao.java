package com.xqx.travel.dao;

import com.xqx.travel.domain.Favorite;

/**
 * @author： xingquanxiang
 * createTime：2019/9/24 21:49
 * description:
 */
public interface FavoriteDao {
    /**
     * 查询有收藏该路线的的用户
     * @param rid
     * @param uid
     * @return
     */
    Favorite isFavorite(int rid, int uid);

    /**
     * 根据路线id查询收藏次数
     * @param rid
     * @return
     */
    Integer findCountByRid(int rid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     * @return
     */
    int add(int rid, int uid);
}
