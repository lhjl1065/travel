package com.lhjl.travel.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhjl.travel.dao.CategoryDao;
import com.lhjl.travel.dao.impl.CategoryDaoImpl;
import com.lhjl.travel.domain.Category;
import com.lhjl.travel.service.CategoryService;
import com.lhjl.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        try {
            //获取jedis链接,先查询redis中有没有category的数据
            Jedis jedis = JedisUtil.getJedis();
            Set<Tuple> categories = jedis.zrangeWithScores("category", 0, -1);
            List<Category> list = null;
            if (categories == null || categories.size() == 0) {
                //没有数据则调用dao层方法去查数据,然后存入redis中
                list = categoryDao.findAll();
                //为了保证顺序,用redis格式中的sortedset来保存对象数组
                for (Category item :
                        list) {
                    jedis.zadd("category", item.getCid(), item.getCname());
                }
            } else {
                list = new ArrayList<Category>();
                //有数据的话转换成对象数组返回
                for (Tuple category :
                        categories) {
                    Category category1 = new Category();
                    category1.setCname(category.getElement());
                    category1.setCid((int) category.getScore());
                    list.add(category1);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

