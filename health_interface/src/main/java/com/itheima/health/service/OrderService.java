package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Order;

import java.util.Map;

public interface OrderService {
    /**
     * 提交体检预约
     *
     * @param orderInfo
     * @return
     */
    Order submit(Map<String, String> orderInfo) throws HealthException;

    /**
     * 预约成功展示
     *
     * @param id
     * @return
     */
    Map findById4Detail(int id);
}
