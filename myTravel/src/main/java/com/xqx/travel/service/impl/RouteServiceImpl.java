package com.xqx.travel.service.impl;

import com.xqx.travel.dao.FavoriteDao;
import com.xqx.travel.dao.RouteDao;
import com.xqx.travel.dao.RouteImgDao;
import com.xqx.travel.dao.SellerDao;
import com.xqx.travel.dao.impl.FavoriteDaoImpl;
import com.xqx.travel.dao.impl.RouteDaoImpl;
import com.xqx.travel.dao.impl.RouteImgDaoImpl;
import com.xqx.travel.dao.impl.SellerDaoImpl;
import com.xqx.travel.domain.PageBean;
import com.xqx.travel.domain.Route;
import com.xqx.travel.domain.RouteImg;
import com.xqx.travel.domain.Seller;
import com.xqx.travel.service.RouteService;

import java.util.List;

/**
 * @author：xingquanxiang createTime：2019/9/23 14:53
 * description:
 */
public class RouteServiceImpl implements RouteService {
    /**
     * 创建dao层对象进行数据查询
     */
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();;

    /**
     * 实现旅游路线分页查询
     *
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        /**
         * 1.创建路线分页查询对象
         */
        PageBean<Route> routePageBean = new PageBean<Route>();
        //2.对象属性赋值
        routePageBean.setCurrentPage(currentPage);
        routePageBean.setPageSize(pageSize);
        //3.计算查询开始位置
        int begin = (currentPage - 1) * pageSize;
        //4.调用dao层分页查询方法
        //4.1 查询总记录数
        int count = routeDao.countQuery(cid, rname);
        routePageBean.setTotalCount(count);
        //4.2 查询记录数据
        routePageBean.setList(routeDao.pageQuery(cid, begin, pageSize, rname));
        //5. 计算总页数并赋值
        routePageBean.setTotalPage(count % pageSize == 0 ? count / pageSize : (count / pageSize) + 1);
        //6.返回分页查询对象
        return routePageBean;
    }

    /**
     * 查询单个旅游路线详细信息
     *
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        Route route = new Route();
        // 1.根据id查询route对象
        route = routeDao.findOne(rid);
        if (route == null) {
            return null;
        }
        // 2.调用RouteImgDao 根据rid线路id查询 tab_route_img,将集合设置到route对象
        List<RouteImg> routeImgList = routeImgDao.findByRid(rid);
        //将查询到的数据封装到route对象中
        route.setRouteImgList(routeImgList);
        // 3.调用SellerDao 根据sid卖家id查询tab_seller查询卖家信息，将其设置到route对象
        Integer sid = route.getSid();
        Seller seller = sellerDao.findOne(sid);
        // 4.查询路线收藏次数 并封装
        route.setCount(favoriteDao.findCountByRid(route.getRid()));

        //将查询到的数据封装到route对象中
        route.setSeller(seller);
        return route;
    }
}
