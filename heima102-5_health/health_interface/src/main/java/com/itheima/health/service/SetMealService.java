package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.SetMeal;

import java.util.List;
import java.util.Map;

public interface SetMealService {
    /**
     * 新增套餐
     *
     * @param setMeal
     * @param checkGroupIds
     */
    void add(SetMeal setMeal, Integer[] checkGroupIds);

    /**
     * 分页条件查询
     *
     * @param queryPageBean
     * @return
     */
    PageResult<SetMeal> findPage(QueryPageBean queryPageBean);

    /**
     * 通过id查询套餐信息
     *
     * @param id
     * @return
     */
    SetMeal findById(int id);

    /**
     * 通过id查询选中的检查组ids
     *
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetMealId(int id);

    /**
     * 编辑体检套餐
     *
     * @param setMeal
     * @param checkGroupIds
     */
    void update(SetMeal setMeal, Integer[] checkGroupIds);

    /**
     * 删除套餐
     *
     * @param id
     */
    void deleteById(int id) throws HealthException;

    /**
     * 查数据中套餐的所有图片
     *
     * @return
     */
    List<String> findImg();

    /**
     * 查询所有的套餐
     *
     * @return
     */
    List<SetMeal> getSetmeal();

    /**
     * 根据套餐id查询套餐详情信息
     *
     * @param id
     * @return
     */
    SetMeal findDetailById(int id) throws HealthException;

    /**
     * 获取套餐的预约数量
     *
     * @return
     */
    List<Map<String, Object>> findSetmealCount();

}
