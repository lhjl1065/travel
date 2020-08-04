package com.lhjl.travel.dao;

import com.lhjl.travel.domain.RouteImg;

import java.util.List;

public interface RouteImageDao {
    List<RouteImg> findByRid(int rid);
}
