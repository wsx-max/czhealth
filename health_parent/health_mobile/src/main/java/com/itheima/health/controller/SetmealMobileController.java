package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;

    //查询所有套餐
    @GetMapping("/getSetmeal")
    public Result getsetmeal() {
        List<Setmeal> list = setmealService.findAll();
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, list);
    }

    //查询套餐详情
    @PostMapping("/findDetailById")
    public Result findDetailById(int id) {
        Setmeal setmeal = setmealService.findDetailById(id);
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());

        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }
}














