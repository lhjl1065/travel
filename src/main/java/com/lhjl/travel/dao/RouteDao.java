package com.lhjl.travel.dao;

import com.lhjl.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    int findTotalCount(int cid, String rname);

    List<Route> findRoute(int currentIndex_, int cid, int pageSize_, String rname);

    Route findOne(int rid);
}
