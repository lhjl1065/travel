package com.lhjl.travel.dao;

import com.lhjl.travel.domain.Favorite;

public interface FavoriteDao {

    Favorite findByRidAndUid(int rid, int uid);

    int findCount(int rid);

    void addFavorite(int rid, int uid);
}
