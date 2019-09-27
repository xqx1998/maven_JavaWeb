package com.xqx.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xqx.travel.domain.LoginUser;
import com.xqx.travel.domain.ResultInfo;
import com.xqx.travel.domain.User;
import com.xqx.travel.service.UserService;
import com.xqx.travel.service.impl.UserServiceImpl;
import com.xqx.travel.util.MailUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author：xingquanxiang createTime：2019/9/22 18:07
 * description:
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    //创建service层业务对象
    private UserService userService = new UserServiceImpl();

    /**
     * 实现注册功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取form表单验证码
        String verifyCode = request.getParameter("verifyCode");
        //获取session域
        HttpSession session = request.getSession();
        //获取存放在session（服务器）中验证码，并赋值给变量
        String checkcodeServer = (String) session.getAttribute("CHECKCODE_SERVER");
        // 销毁session中的验证，保证验证码的唯一性
        session.removeAttribute("CHECKCODE_SERVER");
        //创建响应对象
        ResultInfo info = new ResultInfo();
        // 进行验证码判断是否一致
        if (verifyCode == null || verifyCode.equals("")) {
            info.setFlag(false);
            info.setErrorMsg("验证码不能为空!");
        } else {
            if (checkcodeServer == null || !checkcodeServer.equalsIgnoreCase(verifyCode)) {
                info.setFlag(false);
                info.setErrorMsg("验证码错误！");
            } else {
                System.out.println(request.getParameter("name"));
                // 1.获取表单数据
                Map<String, String[]> map = request.getParameterMap();
                // 2.封装对象
                User user = new User();
                try {
                    BeanUtils.populate(user, map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                // 3.调用service完成注册
                // 4.根据注册结果 响应前端
                String activeCode = userService.register(user);
                if (activeCode != null) {
                    info.setFlag(true);
                    //发送邮件激活码
                    String referer = request.getHeader("Referer");
                    int index = referer.lastIndexOf('/');
                    String path = referer.substring(0, index);
                    System.out.println("path = " + path);
                    String text = "<p>落雨心星旅游网欢迎您的加入，点击此处<a href=\"" + path + "/user/active?activeCode=" + activeCode + "\"><span style='font-size:20px;'>激活</span></a>账户</p>";
                    MailUtils.sendMail(request.getParameter("email"), text, "账户激活");
                } else {
                    info.setFlag(false);
                    info.setErrorMsg("注册失败，用户名已存在！");
                }
            }
        }
        /*// 设置content-type
        response.setContentType("application/json; charset=utf-8");
        //将info对象序列化为json数据，响应到客户端
        response.getWriter().write(new ObjectMapper().writeValueAsString(info));*/
        writeValue(info, response);

    }

    /**
     * 登录功能实现
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.验证码比对
        String verifyCode = request.getParameter("verifyCode");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo resultInfo = new ResultInfo();
        if (verifyCode == null || verifyCode.length() == 0) {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码不能为空");
        } else {
            if (checkcode_server == null || !verifyCode.equalsIgnoreCase(checkcode_server)) {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("验证码错误！");
            } else {
                // 2.获取用户信息
                Map<String, String[]> map = request.getParameterMap();
                User user = new User();
                try {
                    BeanUtils.populate(user, map);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                // 3.调用service查询user
                user = userService.login(user);
                // 4.判断用户是否存在
                if (user == null) {
                    resultInfo.setFlag(false);
                    resultInfo.setErrorMsg("用户名或密码错误！");
                } else {
                    // 5.判断用户是否激活
                    if ("Y".equals(user.getStatus())) {
                        resultInfo.setFlag(true);
                        //在session中存储用户信息
                        session.setAttribute("user", user);
                    } else {  //未激活
                        resultInfo.setFlag(false);
                        resultInfo.setErrorMsg("账户未激活，请去邮箱中激活！");
                        //发送邮件激活码
                        String referer = request.getHeader("Referer");
                        int index = referer.lastIndexOf('/');
                        String path = referer.substring(0, index);
                        String text = "<p>落雨心星旅游网欢迎您的加入，点击此处<a href=\"" + path + "/activeUserServlet?activeCode=" + user.getCode() + "\"><span style='font-size:20px;'>激活</span></a>账户</p>";
                        MailUtils.sendMail(user.getEmail(), text, "账户激活");
                    }
                    //自动登录处理
                    String autoLogin = request.getParameter("autoLogin");
                    //判断是否自动登录
                    if (autoLogin != null && autoLogin.equals("Y")) { //是
                        // 创建cookie对象存储用户名和密码
                        Cookie cookieUsername = new Cookie("travelUsername", user.getUsername());
                        Cookie cookiePassword = new Cookie("travelPassword", user.getPassword());
                        //设置保存时长为一周
                        cookieUsername.setMaxAge(60 * 60 * 24 * 7);
                        cookiePassword.setMaxAge(60 * 60 * 24 * 7);
                        //在响应对象中加入cookie对象
                        response.addCookie(cookieUsername);
                        response.addCookie(cookiePassword);
                    } else { //不自动登录
                        //删除保存的cookie
                        Cookie d_cookie1 = new Cookie("travelUsername", "");
                        Cookie d_cookie2 = new Cookie("travelPassword", "");
                        d_cookie1.setMaxAge(0);
                        d_cookie2.setMaxAge(0);
                        response.addCookie(d_cookie1);
                        response.addCookie(d_cookie2);
                    }
                }

            }
        }
        // 6.响应客户端消息
        writeValue(resultInfo, response);
    }

    /**
     * 退出功能实现
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //删除服务端session中保存的user对象
        request.getSession().removeAttribute("user");
        //重定向跳转到首页
        response.sendRedirect(request.getContextPath() + "/index.html");
    }

    /**
     * 获取登录的用户信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取session中用户信息
        User user = (User) request.getSession().getAttribute("user");
        //设置响应类型
        response.setContentType("application/json; charset=utf-8");
        //获取响应字符流
        PrintWriter pw = response.getWriter();
        if (user != null) {
            //获取用户姓名
            String name = user.getName();
            //向响应字符流中写入json数据
            pw.write(writeValueAsString(new ResultInfo(true, name, "欢迎回来，")));
        } else {
            pw.write(writeValueAsString(new ResultInfo(false, "")));
        }
    }

    /**
     * 自动登录功能
     * 回显客户端表单用户名，密码
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void autoLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取客户端请求中所有的cookie对象
        Cookie[] cookies = request.getCookies();
        //创建响应客户端消息对象
        ResultInfo resultInfo = new ResultInfo();
        //设置flag属性为false
        resultInfo.setFlag(false);
        //创建登录用户对象
        LoginUser loginUser = new LoginUser();
        //遍历cookies数组
        for (Cookie cookie : cookies) {
            //判断cookies数组 中是否包含travelUsername的cookie对象
            if (cookie.getName().equals("travelUsername")) {
                loginUser.setUsername(cookie.getValue());
                resultInfo.setFlag(true);
            }
            if (cookie.getName().equals("travelPassword")) {
                loginUser.setPassword(cookie.getValue());
            }
        }
        //设置响应客户端消息对象data属性为登录用户对象
        resultInfo.setData(loginUser);
        writeValue(resultInfo, response);
    }

    /**
     * 用户邮箱激活功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取激活码
        String activeCode = request.getParameter("activeCode");
        //判断激活码码是否为空
        if (activeCode == null || activeCode.equals("")) {  //是
            response.getWriter().write("激活失败，激活码不存在！");
        } else { //否
            // 调用service层核对激活码是否正确
            if (userService.active(activeCode)) { //正确
                response.getWriter().write("激活账户成功，点击<a href=\"" + request.getContextPath() + "/login.html\"><h1>登录</h1></a>");
            } else { //不正确
                response.getWriter().write("激活账户失败，请联系管理员");
            }
        }
    }
}
