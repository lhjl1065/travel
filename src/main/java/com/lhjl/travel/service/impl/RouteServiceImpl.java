package com.lhjl.travel.service.impl;

import com.lhjl.travel.dao.FavoriteDao;
import com.lhjl.travel.dao.RouteImageDao;
import com.lhjl.travel.dao.SellerDao;
import com.lhjl.travel.dao.impl.FavoriteDaoImpl;
import com.lhjl.travel.dao.impl.RouteDaoImpl;
import com.lhjl.travel.dao.impl.RouteImageDaoImpl;
import com.lhjl.travel.dao.impl.SellerDaoImpl;
import com.lhjl.travel.domain.*;
import com.lhjl.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDaoImpl routeDao = new RouteDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private RouteImageDao routeImageDao = new RouteImageDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();


    @Override
    public PageBean<Route> routeQuery(int currentPage, int pageSize, int cid, String rname) {
        //创建一个pageBean对象用来存放要返回客户端的信息
        PageBean<Route> pageBean = new PageBean<>();
        //设置cid
        pageBean.setCategory(cid);
        //设置当前页码
        pageBean.setCurrentPage(currentPage);
        //设置每页记录数
        pageBean.setPageSize(pageSize);
        //调用dao层查询总记录数
        int totalCount=routeDao.findTotalCount(cid,rname);
        pageBean.setTotalCount(totalCount);
        //计算出总页数并存到pageBean
        int totalPage=totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
        pageBean.setTotalPage(totalPage);
        //算出开始数据位置
        int currentIndex=(currentPage-1)*pageSize;
        //调用dao层方法查询route记录并存入pageBean
        List<Route> list=routeDao.findRoute(currentIndex,cid,pageSize,rname);
        pageBean.setList(list);
        //返回pageBean
        return pageBean;
    }

    @Override
    public Route findOne(String rid) {
        //1.查询route表中的旅游信息route(只有某些部分会被赋值,其余属性需要去其他表中查)
        Route route=routeDao.findOne(Integer.parseInt(rid));
        //2.用sid在seller表中查seller的信息seller
        Seller seller=sellerDao.findOne(route.getSid());
        //3.用根据rid在image表中查到数据集合list
        List<RouteImg> list=routeImageDao.findByRid(route.getRid());
        //4.根据rid到favorite表中查询出收藏人数
        int favorite_count=favoriteDao.findCount(Integer.parseInt(rid));
        //5.把它们封装到route对象
        route.setSeller(seller);
        route.setRouteImgList(list);
        route.setCount(favorite_count);
        return route;
    }

    @Override
    public boolean isFavorite(String rid, int uid) {
        //调用FavoriteDao的findByRidAndUid方法来查询是否收藏
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        if (favorite==null){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public void addFavorite(int rid, int uid) {
        //调用FavoriteDao的addFavorite方法
        favoriteDao.addFavorite(rid,uid);
    }
}
