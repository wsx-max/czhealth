package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    //添加套餐
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //添加套餐
        setmealDao.add(setmeal);

        //获取套餐的id
        Integer setmealId = setmeal.getId();
        //添加套餐与检查组的关系
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId, checkgroupId);
            }
        }
    }

    //分页查询套餐
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //查询t_setmeal
        Page<Setmeal> page = setmealDao.findPage(queryPageBean.getQueryString());
        PageResult<Setmeal> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return pageResult;
    }

    //根据id查询套餐
    @Override
    public Setmeal findById(int id) {
        Setmeal setmeal = setmealDao.findById(id);
        return setmeal;
    }

    // 获取选中的检查组id
    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(int id) {
        return setmealDao.findCheckgroupIdsBySetmealId(id);
    }

    //编辑套餐
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        //编辑套餐
        setmealDao.update(setmeal);
        //获取套餐id
        Integer setmealId = setmeal.getId();
        //删除旧关系
        setmealDao.deleteSetmealCheckGroup(setmealId);
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId, checkgroupId);
            }
        }
    }

    //删除套餐
    @Override
    @Transactional
    public void delete(Integer id) {

        //根据套餐id查询t_order，判断是否被使用
        int cnt = setmealDao.findOrderCountBySetmealId(id);
        if (cnt > 0) {
            //已被使用，不能删除
            throw new HealthException("已经有订单使用了这个套餐，不能删除！");
        }
        //先删除套餐与检查组的关系
        setmealDao.deleteSetmealCheckGroup(id);
        //删除套餐
        setmealDao.deleteById(id);
    }

    //查询所有图片名称
    @Override
    public List<String> findImgs() {
        return setmealDao.findImgs();
    }
}














