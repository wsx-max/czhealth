package com.itheima.health.service;

import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    /**
     * 批量导入
     *
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList);

    /**
     * 通过月份查询预约设置信息
     *
     * @param month
     * @return
     */
    List<Map> getOrderSettingByMonth(String month);

    /**
     * 基于日历的预约设置
     *
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting) throws HealthException;

    /**
     * 通过当天的日期查找之前的历史预约数据
     * @param now
     * @return
     */
    List<OrderSetting> findByOrderDateOfToday(String now);

    /**
     * 删除历史预约数据
     * @param id
     */
    void deleteByTodayId(Integer id);
}
