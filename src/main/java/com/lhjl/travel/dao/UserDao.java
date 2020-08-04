package com.lhjl.travel.dao;

import com.lhjl.travel.domain.User;

public interface UserDao {

    int findByName(String username);

    void addUser(User user);

    int findByCode(String code);

    void updateStatus(String code);

    User findByUserAndPassword(User user);
}
