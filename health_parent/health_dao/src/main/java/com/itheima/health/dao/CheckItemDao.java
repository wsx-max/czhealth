package com.itheima.health.dao;

import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    List<CheckItem> findAll();

    int add(CheckItem checkItem);
}
