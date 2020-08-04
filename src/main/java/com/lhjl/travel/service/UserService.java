package com.lhjl.travel.service;

import com.lhjl.travel.domain.User;

public interface UserService {

    public boolean register(User user);

    boolean active(String code);

    User login(User user);
}
