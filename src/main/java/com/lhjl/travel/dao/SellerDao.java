package com.lhjl.travel.dao;

import com.lhjl.travel.domain.Seller;

public interface SellerDao {
    Seller findOne(int sid);
}
