package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.SetMeal;
import com.itheima.health.service.SetMealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("setmeal")
public class SetmealMobileController {

    @Reference
    private SetMealService setMealService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("getSetmeal")
    public Result getSetmeal() {
        // 查询所有的套餐
        List<SetMeal> setMeals = setMealService.getSetmeal();
        // 套餐里有图片有全路径吗? 拼接全路径
        setMeals.forEach(s -> s.setImg(QiNiuUtils.DOMAIN + s.getImg()));
        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, setMeals);
    }

    /**
     * 根据套餐id查询套餐详情信息
     *
     * @param id
     * @return
     */
    @GetMapping("findDetailById")
    public Result findDetailById(int id) {
        // 调用服务查询详情
        SetMeal setMeal = setMealService.findDetailById(id);
        // 设置图片的完整路径
        setMeal.setImg(QiNiuUtils.DOMAIN + setMeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setMeal);
    }

    /**
     * 套餐基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("findById")
    public Result findById(int id) {
        // 调用服务查询
        SetMeal s = setMealService.findById(id);
        // 图片的完整路径
        s.setImg(QiNiuUtils.DOMAIN + s.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, s);
    }
}
