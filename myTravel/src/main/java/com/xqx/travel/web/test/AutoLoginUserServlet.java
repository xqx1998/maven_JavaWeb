package com.xqx.travel.web.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xqx.travel.domain.LoginUser;
import com.xqx.travel.domain.ResultInfo;
import com.xqx.travel.domain.User;
import javafx.beans.binding.ObjectExpression;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author：xingquanxiang createTime：2019/9/22 14:16
 * description:
 */
@WebServlet("/autoLoginUserServlet")
public class AutoLoginUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setFlag(false);
        LoginUser loginUser = new LoginUser();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("travelUsername")){
                loginUser.setUsername(cookie.getValue());
                resultInfo.setFlag(true);
            }
            if (cookie.getName().equals("travelPassword")){
                loginUser.setPassword(cookie.getValue());
            }
        }
        resultInfo.setData(loginUser);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(resultInfo));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
