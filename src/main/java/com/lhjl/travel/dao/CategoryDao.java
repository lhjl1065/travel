package com.lhjl.travel.dao;

import com.lhjl.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
}
