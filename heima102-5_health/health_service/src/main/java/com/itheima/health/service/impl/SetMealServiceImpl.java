package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.SetMealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.SetMeal;
import com.itheima.health.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetMealService.class)
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealDao setMealDao;

    /**
     * 新增套餐
     *
     * @param setMeal
     * @param checkGroupIds
     */
    @Override
    @Transactional
    public void add(SetMeal setMeal, Integer[] checkGroupIds) {
        // 添加套餐信息
        setMealDao.add(setMeal);
        // 获取套餐的id
        // 添加套餐与检查组的关系
        if (checkGroupIds != null) {
            for (Integer checkGroupId : checkGroupIds) {
                setMealDao.addSetMealCheckGroup(setMeal.getId(), checkGroupId);
            }
        }
    }

    /**
     * 分页条件查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<SetMeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 查询条件
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            // 模糊查询 %
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 条件查询，这个查询语句会被分页
        Page<SetMeal> page = setMealDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<SetMeal>(page.getTotal(), page.getResult());
    }

    /**
     * 通过id查询套餐信息
     *
     * @param id
     * @return
     */
    @Override
    public SetMeal findById(int id) {
        return setMealDao.findById(id);

    }

    /**
     * 通过id查询选中的检查组ids
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetMealId(int id) {
        return setMealDao.findCheckGroupIdsBySetMealId(id);
    }

    /**
     * 编辑体检套餐
     *
     * @param setMeal
     * @param checkGroupIds
     */
    @Override
    @Transactional
    public void update(SetMeal setMeal, Integer[] checkGroupIds) {
        // 先更新套餐信息
        setMealDao.update(setMeal);
        // 删除旧关系
        setMealDao.deleteSetMealCheckGroup(setMeal.getId());
        // 添加新关系
        if (checkGroupIds != null) {
            for (Integer checkGroupId : checkGroupIds) {
                setMealDao.addSetMealCheckGroup(setMeal.getId(), checkGroupId);
            }
        }
    }

    /**
     * 删除套餐
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) throws HealthException {
        // 是否存在订单，如果存在则不能删除
        int cnt = setMealDao.findOrderCountBySetMealId(id);
        if (cnt > 0) {
            throw new HealthException("已经有订单使用了这个套餐，不能删除！");
        }
        // 先删除套餐与检查组的关系
        setMealDao.deleteSetMealCheckGroup(id);
        // 再删除套餐
        setMealDao.deleteById(id);
    }

    /**
     * 查数据中套餐的所有图片
     *
     * @return
     */
    @Override
    public List<String> findImg() {
        return setMealDao.findImg();
    }

    /**
     * 查询所有的套餐
     *
     * @return
     */
    @Override
    public List<SetMeal> getSetmeal() {
        return setMealDao.findAll();
    }

    /**
     * 根据套餐id查询套餐详情信息
     *
     * @param id
     * @return
     */
    @Override
    public SetMeal findDetailById(int id) {
        return setMealDao.findDetailById(id);
    }

    /**
     * 获取套餐的预约数量
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setMealDao.findSetmealCount();
    }
}
