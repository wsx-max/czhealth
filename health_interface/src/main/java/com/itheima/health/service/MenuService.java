package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> findByUserId(String username) throws HealthException;
}
