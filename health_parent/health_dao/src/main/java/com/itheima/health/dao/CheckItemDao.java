package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    List<CheckItem> findAll();

    int add(CheckItem checkItem);

    Page<CheckItem> findByCondition(String queryString);

    int deleteById(Integer id);

    int findCountByCheckItemId(Integer id);
}
