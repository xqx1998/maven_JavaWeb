package com.xqx.travel.web.test;

import com.xqx.travel.service.UserService;
import com.xqx.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author：xingquanxiang createTime：2019/9/21 19:01
 * description:
 */
@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String activeCode = request.getParameter("activeCode");
        UserService userService = new UserServiceImpl();
        if(activeCode == null || activeCode.equals("")){
            response.getWriter().write("激活失败，激活码不存在！");
        }else {
            if (userService.active(activeCode)) {
                response.getWriter().write("激活账户成功，点击<a href=\"" + request.getContextPath() + "/login.html\"><h1>登录</h1></a>");
            }else{
                response.getWriter().write("激活账户失败，请联系管理员");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
