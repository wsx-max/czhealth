package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 批量导入
     *
     * @param orderSettingList
     */
    @Override
    @Transactional
    public void add(List<OrderSetting> orderSettingList) {
        // 遍历
        for (OrderSetting orderSetting : orderSettingList) {
            // 判断是否存在, 通过日期来查询, 注意：日期里是否有时分秒，数据库里的日期是没有时分秒的
            OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
            if (osInDB != null) {
                // 数据库存在这个预约设置, 已预约数量不能大于可预约数量
                if (osInDB.getReservations() > orderSetting.getNumber()) {
                    throw new HealthException(orderSetting.getOrderDate() + " 中已预约数量不能大于可预约数量");
                }
                orderSettingDao.updateNumber(orderSetting);
            } else {
                // 不存在
                orderSettingDao.add(orderSetting);
            }
        }
    }

    /**
     * 通过月份查询预约设置信息
     *
     * @param month
     * @return
     */
    @Override
    public List<Map> getOrderSettingByMonth(String month) {
        month = month + "%";
        //查询当前月份的预约设置
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(month);
        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap<>();
            orderSettingMap.put("date", orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number", orderSetting.getNumber());//可预约人数
            orderSettingMap.put("reservations", orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }
        return data;
    }

    /**
     * 基于日历的预约设置
     *
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) throws HealthException {
        //通过日期判断预约设置是否存在？
        OrderSetting os = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        if (os != null) {
            //- 存在：
            // 判断已经预约的人数是否大于要更新的最大可预约人数， Reservations > 传进来的number数量，则不能更新，要报错
            if (os.getReservations() > orderSetting.getNumber()) {
                // 已经预约的人数高于最大预约人数，不允许
                throw new HealthException("最大预约人数不能小于已预约人数！");
            }
            // Reservations <= 传进来的number数量，则要更新最大可预约数量
            orderSettingDao.updateNumber(orderSetting);
        } else {
            //- 不存在：
            //  - 添加预约设置信息
            orderSettingDao.add(orderSetting);
        }
    }
}
