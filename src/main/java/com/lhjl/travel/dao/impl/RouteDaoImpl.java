package com.lhjl.travel.dao.impl;

import com.lhjl.travel.dao.RouteDao;
import com.lhjl.travel.domain.Route;
import com.lhjl.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid, String rname) {
        if (rname.equals("null") || rname == "") {
            String sql = "select count(rid) from tab_route where cid=? ";
            return jdbcTemplate.queryForObject(sql, Integer.class, cid);
        } else {
            String sql = "select count(rid) from tab_route where cid=? and rname like ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, cid, "%" + rname + "%");
        }


    }

    @Override
    public List<Route> findRoute(int currentIndex_, int cid, int pageSize_, String rname) {
        if (rname.equals("null") || rname == "") {
            String sql = "SELECT * FROM tab_route WHERE cid=? LIMIT ?,?";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), cid, currentIndex_, pageSize_);
        } else {
            String sql = "select * from tab_route where cid=? and rname like ? limit ?,?";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), cid, "%" + rname + "%", currentIndex_, pageSize_);
        }

    }

    @Override
    public Route findOne(int rid) {
        String sql = "SELECT * FROM tab_route WHERE rid=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class),rid);
}
}
