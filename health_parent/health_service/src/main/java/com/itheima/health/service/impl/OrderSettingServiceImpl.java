package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    ////上传Excel文件
    @Override
    public void add(List<OrderSetting> orderSettingList) throws HealthException {

        //遍历
        for (OrderSetting orderSetting : orderSettingList) {
            //判断是否存在，通过日期来查询，注意：日期里是否有时分秒，数据库里的日期是没有时分秒的
            OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
            if (osInDB != null) {
                //数据库存在这个预约设置, 已预约数量不能大于可预约数量
                if (osInDB.getReservations() > orderSetting.getNumber()) {
                    throw new HealthException(orderSetting.getOrderDate() + " 中已预约数量不能大于可预约数量");
                }
                //更新可预约数量
                orderSettingDao.updateNumber(orderSetting);
            } else {
                // 不存在
                orderSettingDao.add(orderSetting);

            }
        }
    }

    //通过月份查询预约设置信息
    @Override
    public List<Map> getOrderSettingByMonth(String month) {
        // 1.组织查询Map，dateBegin表示月份开始时间，dateEnd月份结束时间
        String dateBegin = month + "-1";
        String dateEnd = month + "-31";
        Map<String, String> map = new HashMap<>();
        map.put("dateBegin", dateBegin);
        map.put("dateEnd", dateEnd);
        //查询当前月份的预约设置
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);

        List<Map> mapList = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date", orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number", orderSetting.getNumber());
            orderSettingMap.put("reservations", orderSetting.getReservations());
            mapList.add(orderSettingMap);
        }

        return mapList;

    }

    //基于日历的预约设置
    @Override
    public void editNumberByDate(OrderSetting orderSetting) throws HealthException {
        //查询要设置日期的的orderSetting对象
        OrderSetting byOrderDate = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        if (null != byOrderDate) {
            if (byOrderDate.getReservations() > orderSetting.getNumber()) {
                throw new HealthException("可预约人数不能小于已预约人数");
            }
            orderSettingDao.updateNumber(orderSetting);
        } else {
            orderSettingDao.add(orderSetting);
        }
    }
}












