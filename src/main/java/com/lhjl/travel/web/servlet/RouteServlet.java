package com.lhjl.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhjl.travel.domain.PageBean;
import com.lhjl.travel.domain.Route;
import com.lhjl.travel.domain.User;
import com.lhjl.travel.service.RouteService;
import com.lhjl.travel.service.impl.RouteServiceImpl;
import com.lhjl.travel.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService=new RouteServiceImpl();

    /**
     * 分页查询
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String cid = request.getParameter("cid");
        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        String rname = request.getParameter("rname");
        System.out.println(rname);

        //如果不传递cid默认为5
        if (cid==null||cid.length()==0||cid.equals("null")){
            cid="5";
        }
        //如果不传递页码,默认为第页
        if (currentPage==null||currentPage.length()==0){
            currentPage="1";
        }
        //如果不传递每页条数,默认为5
        if (pageSize==null||pageSize.length()==0){
            pageSize="5";
        }
        int pageSize_ = Integer.parseInt(pageSize);
        int cid_ = Integer.parseInt(cid);
        int currentPage_ = Integer.parseInt(currentPage);
        //调用RouteService的routeQuery方法查询PageBean
        PageBean<Route> pageBean = routeService.routeQuery(currentPage_, pageSize_, cid_,rname);
        giveJson(pageBean,response);
    }

    /**
     * 根据id查询一条旅游路线的具体信息
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //1.获取rid
        String rid = request.getParameter("rid");
        //2.根据rid调用Service查询Route对象
        Route route=routeService.findOne(rid);
        //3.返回json对象
        giveJson(route,response);
    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //1.获取rid和user对象
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        //2.判断是否登录,没有登录rid
        if (user==null){
            uid=0;
        }
        else{
            uid=user.getUid();
        }
        //2.调用RouteService的isFavorite方法判断是否rid是否被当用户收藏
        boolean flag=routeService.isFavorite(rid,uid);
        giveJson(flag,response);
    }
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //1.获取rid和user对象
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return;
        }
        int uid=user.getUid();
        //2.调用RouteService的addFavorite方法添加收藏
        routeService.addFavorite(Integer.parseInt(rid),uid);
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
