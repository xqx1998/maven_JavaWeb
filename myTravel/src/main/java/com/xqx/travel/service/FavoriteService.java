package com.xqx.travel.service;

import com.xqx.travel.domain.User;

/**
 * @author： xingquanxiang
 * createTime：2019/9/24 21:45
 * description:
 */
public interface FavoriteService {
    /**
     * 判断该路线是否被本次登录的用户收藏
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(int rid, int uid);

    boolean addFavorite(int rid, User user);
}
