package com.xqx.travel.web.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xqx.travel.domain.ResultInfo;
import com.xqx.travel.domain.User;

import javax.lang.model.element.VariableElement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author：xingquanxiang createTime：2019/9/22 11:21
 * description:
 */
@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取session中用户信息
        User user = (User) request.getSession().getAttribute("user");
        //设置响应类型
        response.setContentType("application/json; charset=utf-8");
        //获取响应字符流
        PrintWriter pw = response.getWriter();
        if (user!=null){
            //获取用户姓名
            String name = user.getName();
            pw.write(new ObjectMapper().writeValueAsString(new ResultInfo(true,name,"欢迎回来，")));
        }else{
            pw.write(new ObjectMapper().writeValueAsString(new ResultInfo(false,"")));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
