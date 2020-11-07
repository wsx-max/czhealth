package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.SetMeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetMealDao {
    /**
     * 添加套餐
     *
     * @param setMeal
     */
    void add(SetMeal setMeal);

    /**
     * 添加套餐与检查组的关系
     *
     * @param setMealId
     * @param checkGroupId
     */
    void addSetMealCheckGroup(@Param("setMealId") Integer setMealId, @Param("checkGroupId") Integer checkGroupId);

    /**
     * 分页条件查询
     *
     * @param queryString
     * @return
     */
    Page<SetMeal> findByCondition(String queryString);

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
     * 更新套餐信息
     *
     * @param setMeal
     */
    void update(SetMeal setMeal);

    /**
     * 删除旧关系
     *
     * @param id
     */
    void deleteSetMealCheckGroup(Integer id);

    /**
     * 通过套餐的id查询使用了这个套餐的订单个数
     *
     * @param id
     * @return
     */
    int findOrderCountBySetMealId(int id);

    /**
     * 通过id删除套餐信息
     *
     * @param id
     */
    void deleteById(int id);

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
    List<SetMeal> findAll();

    /**
     * 根据套餐id查询套餐详情信息
     *
     * @param id
     * @return
     */
    SetMeal findDetailById(int id);

    /**
     * 获取套餐的预约数量
     *
     * @return
     */
    List<Map<String, Object>> findSetmealCount();

}
