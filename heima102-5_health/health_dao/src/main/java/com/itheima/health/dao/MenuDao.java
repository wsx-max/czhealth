package com.itheima.health.dao;

import com.itheima.health.pojo.Menu;

import java.util.List;

public interface MenuDao {
    List<Menu> findByUserId(String username);
}
