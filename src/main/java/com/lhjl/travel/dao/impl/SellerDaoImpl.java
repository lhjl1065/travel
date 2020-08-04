package com.lhjl.travel.dao.impl;

import com.lhjl.travel.dao.SellerDao;
import com.lhjl.travel.domain.Seller;
import com.lhjl.travel.domain.User;
import com.lhjl.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Seller findOne(int sid) {
        String sql = "SELECT * FROM tab_seller WHERE sid=?";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }
}
