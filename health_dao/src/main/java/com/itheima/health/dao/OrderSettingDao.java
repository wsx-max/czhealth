package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {
    /**
     * 通过日期来查询预约设置
     *
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 更新可预约数量
     *
     * @param orderSetting
     */
    void updateNumber(OrderSetting orderSetting);

    /**
     * 添加预约设置
     *
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 通过月份查询预约设置信息
     *
     * @param month
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth(String month);

    /**
     * 更新已预约人数，如果成功则返回1，没有更新到数据则返回0
     *
     * @param orderSetting
     * @return
     */
    int editReservationsByOrderDate(OrderSetting orderSetting);

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
