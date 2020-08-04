package com.lhjl.travel.service;

import com.lhjl.travel.domain.PageBean;
import com.lhjl.travel.domain.Route;

public interface RouteService  {

    PageBean<Route> routeQuery(int currentPage, int pageSize, int cid, String rname);

    Route findOne(String rid);

    boolean isFavorite(String rid, int uid);

    void addFavorite(int rid, int uid);
}
