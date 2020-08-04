package com.lhjl.travel.dao.impl;

import com.lhjl.travel.dao.FavoriteDao;
import com.lhjl.travel.domain.Favorite;
import com.lhjl.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        try {
            String sql = "SELECT * FROM tab_favorite where rid=? and uid=?";
            Favorite favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
            return favorite;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int findCount(int rid) {
        String sql = "SELECT count(*) FROM tab_favorite where rid=?";
        int count= jdbcTemplate.queryForObject(sql,Integer.class,rid);
        return count;
    }

    @Override
    public void addFavorite(int rid, int uid) {
        String sql = "INSERT INTO tab_favorite VALUES(?,?,?)";
        jdbcTemplate.update(sql,rid,new Date(),uid);
    }
}
