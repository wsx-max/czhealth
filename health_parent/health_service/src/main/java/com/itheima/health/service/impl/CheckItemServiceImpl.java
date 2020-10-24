package com.itheima.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    //查询所有检查项
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    //添加检查项
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    //分页查询检查项
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 模糊查询 拼接 %
        // 判断是否有查询条件
        //queryPageBean.getQueryString() != null && queryPageBean.getQueryString().length() > 0
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            // 有查询条件，拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 紧接着的查询语句会被分页
        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());
        // 封装到分页结果对象中
        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal(), page.getResult());
        return pageResult;
    }

    //删除检查项
    @Override
    public void deleteById(Integer id) throws HealthException {
        //判断该检查项是否被检查组使用
        //查询t_checkgroup_checkitem
        int cnt = checkItemDao.findCountByCheckItemId(id);
        if (cnt > 0) {
            // 已经被检查组使用了，则不能删除，报错
            throw new HealthException(MessageConstant.CHECKITEM_IN_USE);
        }
        checkItemDao.deleteById(id);
    }

    //根据id查询检查项
    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    //更新检查项
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
}
















