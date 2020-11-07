package com.itheima.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 定时清理预约设置历史数据
 */
@Component
@Slf4j
public class OrderSettingJob {
    /**
     * 订阅服务
     */
    @Reference
    private OrderSettingService orderSettingService;

    /*@Scheduled(cron = "0/10 * * * * ?")*/
    @Scheduled(cron = "0 0 2 L * ? *") //每月最后一天凌晨2点执行一次清理任务
    public void orderSettingJob(){
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        /*String now = "2020-03-05";*/
        //调用服务找出今天之前的所有可预约数和已预约数
        List<OrderSetting> orderSetting = orderSettingService.findByOrderDateOfToday(now);

        orderSetting.forEach(ord->{
            log.info("查出今天以前的预约历史数据",ord);
            //删除今天以前的历史预约数据
            orderSettingService.deleteByTodayId(ord.getId());
            log.info("删除成功",ord);
        });


    }
}
