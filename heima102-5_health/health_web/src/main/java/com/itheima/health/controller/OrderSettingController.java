package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("orderSetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 批量导入
     *
     * @param excelFile
     * @return
     * @throws Exception
     */
    @PostMapping("upload")
    public Result upload(MultipartFile excelFile) throws Exception {
        // 读取excel内容
        List<String[]> excel = POIUtils.readExcel(excelFile);
        // 转成List<OrderSetting>
        List<OrderSetting> orderSettingList = new ArrayList<>();
        // 日期格式解析
        SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
        Date orderDate = null;
        OrderSetting os = null;
        for (String[] dataArr : excel) {
            orderDate = sdf.parse(dataArr[0]);
            Integer number = Integer.valueOf(dataArr[1]);
            os = new OrderSetting(orderDate, number);
            orderSettingList.add(os);
        }
        // 调用业务服务
        orderSettingService.add(orderSettingList);
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    /**
     * 通过月份查询预约设置信息
     *
     * @param month
     * @return
     */
    @GetMapping("getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month) {
        // 调用服务端查询
        List<Map> data = orderSettingService.getOrderSettingByMonth(month);
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, data);
    }

    /**
     * 基于日历的预约设置
     *
     * @return
     */
    @PostMapping("editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        orderSettingService.editNumberByDate(orderSetting);
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}
