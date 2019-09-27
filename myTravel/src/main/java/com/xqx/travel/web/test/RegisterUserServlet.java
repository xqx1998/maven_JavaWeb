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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author：xingquanxiang createTime：2019/9/21 12:01
 * description:
 */
@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            if (checkcodeServer==null || !checkcodeServer.equalsIgnoreCase(verifyCode)) {
                info.setFlag(false);
                info.setErrorMsg("验证码错误！");
            } else {
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
                UserService userService = new UserServiceImpl();
                // 4.根据注册结果 响应前端
                String activeCode = userService.register(user);
                if (activeCode!=null) {
                    info.setFlag(true);
                    //发送邮件激活码
                    String referer = request.getHeader("Referer");
                    int index = referer.lastIndexOf('/');
                    String path = referer.substring(0, index);
                    String text = "<p>落雨心星旅游网欢迎您的加入，点击此处<a href=\""+path+"/activeUserServlet?activeCode="+activeCode+"\"><span style='font-size:20px;'>激活</span></a>账户</p>";
                    MailUtils.sendMail(request.getParameter("email"),text,"账户激活");
                } else {
                    info.setFlag(false);
                    info.setErrorMsg("注册失败，用户名已存在！");
                }
            }
        }
        // 设置content-type
        response.setContentType("application/json; charset=utf-8");
        //将info对象序列化为json数据，响应到客户端
        response.getWriter().write(new ObjectMapper().writeValueAsString(info));

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
