package com.xqx.travel.web.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xqx.travel.domain.ResultInfo;
import com.xqx.travel.domain.User;
import com.xqx.travel.service.UserService;
import com.xqx.travel.service.impl.UserServiceImpl;
import com.xqx.travel.util.MailUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author：xingquanxiang createTime：2019/9/22 9:35
 * description:
 */
@WebServlet("/loginUserServlet")
public class LoginUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                UserService userService = new UserServiceImpl();
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
                        session.setAttribute("user",user);
                    } else {  //未激活
                        resultInfo.setFlag(false);
                        resultInfo.setErrorMsg("账户未激活，请去邮箱中激活！");
                        //发送邮件激活码
                        String referer = request.getHeader("Referer");
                        int index = referer.lastIndexOf('/');
                        String path = referer.substring(0, index);
                        String text = "<p>落雨心星旅游网欢迎您的加入，点击此处<a href=\""+path+"/activeUserServlet?activeCode="+user.getCode()+"\"><span style='font-size:20px;'>激活</span></a>账户</p>";
                        MailUtils.sendMail(user.getEmail(),text, "账户激活");
                    }
                    //自动登录处理
                    String autoLogin = request.getParameter("autoLogin");
                    if(autoLogin!=null && autoLogin.equals("Y")){
                        /*User cookieUser = new User();
                        cookieUser.setUsername(user.getUsername());
                        cookieUser.setPassword(user.getPassword());*/
                        HashMap<String, String> cookieMap = new HashMap<>();

                        Cookie cookieUsername = new Cookie("travelUsername", user.getUsername());
                        Cookie cookiePassword = new Cookie("travelPassword", user.getPassword());
                        cookieUsername.setMaxAge(60*60*24*7);
                        cookiePassword.setMaxAge(60*60*24*7);
                        response.addCookie(cookieUsername);
                        response.addCookie(cookiePassword);
                    }else {
                        Cookie d_cookie1 = new Cookie("travelUsername","");
                        Cookie d_cookie2 = new Cookie("travelPassword","");
                        d_cookie1.setMaxAge(0);
                        d_cookie2.setMaxAge(0);
                        response.addCookie(d_cookie1);
                        response.addCookie(d_cookie2);
                    }
                }

            }
        }
        // 6.响应错误消息
        // 设置content-type
        response.setContentType("application/json; charset=utf-8");
        //将info对象序列化为json数据，响应到客户端
        response.getWriter().write(new ObjectMapper().writeValueAsString(resultInfo));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
