package com.xqx.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xqx.travel.domain.Category;
import com.xqx.travel.service.CategoryService;
import com.xqx.travel.service.UserService;
import com.xqx.travel.service.impl.CategoryServiceImpl;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author：xingquanxiang createTime：2019/9/22 19:54
 * description:
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryServiceImpl();

    /**
     * 查询所有路线分类
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> cs = categoryService.findAll();
        System.out.println("servlet");
        writeValue(cs,response);
    }

   /* public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }*/
}
