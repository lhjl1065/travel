package com.lhjl.travel.dao.impl;

import com.lhjl.travel.dao.UserDao;
import com.lhjl.travel.domain.User;
import com.lhjl.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    //DAO层使用spring-jdbc技术
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findByName(String username) {
        //1.创建sql语句
        String sql = "select count(uid) from tab_user where username=?";
        //2.执行sql语句
        int number = jdbcTemplate.queryForObject(sql, Integer.class, username);
        System.out.println("number:" + number);
        return number;

    }

    @Override
    public void addUser(User user) {
        //1.定义SQL语句
        String sql = "insert into tab_user values(?,?,?,?,?,?,?,?,?,?)";
        //2.执行sql
        jdbcTemplate.update(sql, null, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(),
                user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
    }


    @Override
    public int findByCode(String code) {
        String sql = "select count(uid) from tab_user where code=?";
        int number = jdbcTemplate.queryForObject(sql, Integer.class, code);
        return number;
    }

    @Override
    public void updateStatus(String code) {
        String sql = "update tab_user set status='Y' where code=?";
        jdbcTemplate.update(sql, code);
    }

    @Override
    public User findByUserAndPassword(User user) {
        try {
            String sql = "SELECT * FROM tab_user WHERE username=? AND PASSWORD=?";
            User user1 = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),user.getUsername(),user.getPassword());
            return user1;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
