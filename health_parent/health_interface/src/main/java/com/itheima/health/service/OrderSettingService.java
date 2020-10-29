package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    void add(List<OrderSetting> orderSettingList) throws HealthException;

    List<Map> getOrderSettingByMonth(String month);

    void editNumberByDate(OrderSetting orderSetting) throws HealthException;
}
