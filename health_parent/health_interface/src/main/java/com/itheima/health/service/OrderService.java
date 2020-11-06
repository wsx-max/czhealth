package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Order;

import java.util.Map;

public interface OrderService {
    Order submitOrder(Map<String, String> paraMap) throws HealthException;

    Map<String, Object> findOrderDetailById(int id);
}
