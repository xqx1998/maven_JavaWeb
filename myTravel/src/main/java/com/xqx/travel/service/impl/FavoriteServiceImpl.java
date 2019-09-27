package com.xqx.travel.service.impl;

import com.xqx.travel.dao.FavoriteDao;
import com.xqx.travel.dao.RouteDao;
import com.xqx.travel.dao.impl.FavoriteDaoImpl;
import com.xqx.travel.dao.impl.RouteDaoImpl;
import com.xqx.travel.domain.User;
import com.xqx.travel.service.FavoriteService;

/**
 * @author：xingquanxiang createTime：2019/9/24 21:45
 * description:
 */
public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();

    /**
     * 判断该路线是否被本次登录的用户收藏
     *
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(int rid, int uid) {
        if (favoriteDao.isFavorite(rid, uid) != null) {
            return true;
        }
        return false;
    }

    /**
     * 旅游路线添加收藏
     *
     * @param rid
     * @param user
     */
    @Override
    public boolean addFavorite(int rid, User user) {
        if (rid != 0 && user != null) {
            if (favoriteDao.add(rid, user.getUid())==1){
                if (routeDao.updateRouteCountById(rid)==1) {
                    System.out.println("路线"+rid+"添加收藏成功！");
                    return true;
                }
            }
        }
        return false;
    }
}
