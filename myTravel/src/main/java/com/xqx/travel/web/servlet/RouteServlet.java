package com.xqx.travel.web.servlet;

import com.xqx.travel.domain.PageBean;
import com.xqx.travel.domain.ResultInfo;
import com.xqx.travel.domain.Route;
import com.xqx.travel.domain.User;
import com.xqx.travel.service.FavoriteService;
import com.xqx.travel.service.RouteService;
import com.xqx.travel.service.impl.FavoriteServiceImpl;
import com.xqx.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author：xingquanxiang createTime：2019/9/23 14:49
 * description: 旅游路线视图控制
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    /**
     * 创建服务层对象，进行调用
     */
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.接收请求参数
        //分类id
        String cidStr = request.getParameter("cid");
        //显示当前码
        String currentPageStr = request.getParameter("currentPage");
        //每页显示条数
        String pageSizeStr = request.getParameter("pageSize");

        String rname = request.getParameter("rname");
        if (rname != null && rname.length() > 0) {
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        }
        /**
         * 2.进行数据处理
         */
        //分类id 默认
        int cid = 0;
        if (cidStr != null && !"".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        //默认当前页
        int currentPage = 1;
        if (currentPageStr != null && !"".equals(currentPageStr)) {
            int parseInt = Integer.parseInt(currentPageStr);
            // 判断当前页码是否大于0
            if (parseInt > 0) {
                //大于，则赋值
                currentPage = parseInt;
            }
        }
        //默认每页显示记录条数
        int pageSize = 5;
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        if ("null".equals(rname)) {
            rname = null;
        }
        System.out.println("rname = " + rname);
        /**
         * 3.调用service层分页查询
         */
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);

        /**
         * 4.将查询到的数据json格式化并发送道客户端
         */
        writeValue(routePageBean, response);
    }

    /**
     * 旅游线路详情查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收请求参数
        String ridStr = request.getParameter("rid");
        //判断参数是否为空
        if (ridStr != null && ridStr.length() > 0 && !"null".equals(ridStr)) {
            //不为空
            //将请求参数rid 路线id 转型
            int rid = Integer.parseInt(ridStr);
            //调用service层方法进行查询
            Route route = routeService.findOne(rid);
            writeValue(route,response);
        }/*else{
            writeValue(new ResultInfo(false));
        }*/
    }
    /**
     * 判断当前登录用户是否收藏过该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response){
        boolean flag = false;
        String ridStr = request.getParameter("rid");
        int rid = 0;
        try {
            rid = Integer.parseInt(ridStr);
        } catch (NumberFormatException e) {
            System.out.println("method:isFavorite 数据类型转换异常");
        }

        int uid = 0;
        User user = (User)request.getSession().getAttribute("user");
        if (user!=null){
            uid = user.getUid();
        }

        if (rid!=0 && uid!=0){
            flag = favoriteService.isFavorite(rid,uid);
        }
        writeValue(flag,response);
    }

    /**
     * 旅游路线添加收藏
     * @param request
     * @param response
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response){
        // 1.获取路线 id rid
        String ridStr = request.getParameter("rid");
        int rid = 0;
        try {
            rid = Integer.parseInt(ridStr);
        } catch (NumberFormatException e) {
            System.out.println("servlet_RouteServlet_addFavorite数据转换异常 ");
        }

        // 2.获取用户对象 uid
        User user = (User)request.getSession().getAttribute("user");
        ResultInfo resultInfo = new ResultInfo();
        if (user==null) {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("请先登陆再收藏！");
            writeValue(resultInfo,response);
            return;
        }
        if (favoriteService.isFavorite(rid,user.getUid())) {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("已收藏！");
            writeValue(resultInfo,response);
            return;
        }
        // 3.调用service添加
        boolean flag = favoriteService.addFavorite(rid, user);
        resultInfo.setFlag(flag);
        writeValue(resultInfo,response);
    }
}
