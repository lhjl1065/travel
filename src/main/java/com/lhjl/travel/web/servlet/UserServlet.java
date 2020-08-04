package com.lhjl.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lhjl.travel.domain.ResultInfo;
import com.lhjl.travel.domain.User;
import com.lhjl.travel.service.UserService;
import com.lhjl.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        if (code != null) {
            boolean flag = userService.active(code);
            String show = null;
            response.setContentType("text/html;charset=utf-8");
            if (flag) {
                show = "<h1 STYLE=\"text-align: center\">成功激活,<a href='/travel/login.html'>请登陆</a></h1>";
            } else {
                show = "<h1 STYLE=\"text-align: center\">激活失败,请联系管理员,<a href='/travel/index.html'>返回首页</a></h1>";
            }
            response.getWriter().write(show);
        }
    }

    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.销毁这个浏览器请求对应的session
        request.getSession().invalidate();
        //2.跳转到登录页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    public void findOneName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //出来异步请求获取Session中user对象的name
        User user = (User) request.getSession().getAttribute("user");
        giveJson(user,response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String checkCode = request.getParameter("check");
        String checkCodeReal = (String) request.getSession().getAttribute("checkCode");
        request.getSession().removeAttribute("checkCode");
        if (!checkCode.equalsIgnoreCase(checkCodeReal)) {
            //如果验证码错误
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");

            giveJson(info,response);
            return;
        }
        //1.获取参数
        Map<String, String[]> map = request.getParameterMap();
        //2封装user对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用Service的login方法返回要登陆的user对象
        User loginUser = userService.login(user);
        ResultInfo info = new ResultInfo();
        if (loginUser == null) {
            //账号或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        if (loginUser != null && loginUser.getStatus().equals("Y")) {
            //用户已经激活
            request.getSession().setAttribute("user", loginUser);
            info.setFlag(true);
        }
        if (loginUser != null && loginUser.getStatus().equals("N")) {
            //用户还没有激活
            info.setFlag(false);
            info.setErrorMsg("您尚未邮件激活,请激活");
        }
        giveJson(info,response);
    }

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取Session中的验证码
        String checkCode = request.getParameter("checkCode");
        String checkCodeReal = (String) request.getSession().getAttribute("checkCode");
        //即使删掉验证码,防止恶意回退网页骗过验证
        request.getSession().removeAttribute("checkCode");
        //如果验证码错误,直接返回info
        if (checkCodeReal == null || !checkCodeReal.equalsIgnoreCase(checkCode)) {

            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            giveJson(info,response);
            return;
        }
        //1.获取参数
        Map<String, String[]> map = request.getParameterMap();
        //2.封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(user);
        //3.调用Service的register方法
        boolean flag = userService.register(user);
        //用info对象来记录状态和提示信息
        ResultInfo info = new ResultInfo();
        //如果注册成功
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("用户名已存在");
        }
        giveJson(info,response);
    }
    private String object_json(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(obj);
        return json;
    }
    private void giveJson(Object obj,HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(),obj);
    }
}
