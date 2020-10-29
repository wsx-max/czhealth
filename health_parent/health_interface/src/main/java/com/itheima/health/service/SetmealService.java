package com.itheima.health.service;


import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    //添加套餐
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    //分页查询套餐
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    //根据id查询套餐
    Setmeal findById(int id);

    //查询检查组id
    List<Integer> findCheckgroupIdsBySetmealId(int id);

    //编辑套餐
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    //删除套餐
    void delete(Integer id) throws HealthException;

    //查询所有图片名称
    List<String> findImgs();
}
