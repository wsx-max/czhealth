package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    //添加检查组
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组
        checkGroupDao.add(checkGroup);
        //获取检查组的id
        Integer checkGroupId = checkGroup.getId();

        if (null != checkitemIds) {
            //有勾选
            for (Integer checkitemId : checkitemIds) {
                //添加检查组与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkitemId);
            }
        }

    }

    //分页查询检查组
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 有查询条件的处理, 模糊查询
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(page.getTotal(), page.getResult());
    }

    //根据检查组id查询检查组
    @Override
    public CheckGroup findById(Integer checkGroupId) {
        return checkGroupDao.findById(checkGroupId);
    }

    //根据检查组id查询对应的检查项id
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }

    //编辑检查组
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组
        checkGroupDao.update(checkGroup);
        //删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        if (null != checkitemIds) {
            //有勾选
            for (Integer checkitemId : checkitemIds) {
                //添加检查组与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(), checkitemId);
            }
        }
    }

    //根据id删除检查组
    @Override
    @Transactional
    public void deleteById(int id){
        //判断该检查组是否被套餐使用
        //查询t_setmeal_checkgroup
        int cnt = checkGroupDao.findCountByCheckGroupId(id);
        if (cnt > 0) {
            // 已经被套餐使用了，则不能删除，报错
            throw new HealthException(MessageConstant.CHECKITEM_IN_USE);
        }
        // 没有被套餐使用,就可以删除数据
        // 先删除检查组与检查项的关系
        checkGroupDao.deleteCheckGroupCheckItem(id);
        checkGroupDao.deleteById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}











