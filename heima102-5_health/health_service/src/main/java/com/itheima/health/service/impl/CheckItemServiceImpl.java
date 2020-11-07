package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
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

    /**
     * 查询 所有检查项
     *
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> checkItemList = checkItemDao.findAll();
        return checkItemList;
    }

    /**
     * 新增检查项
     *
     * @param checkItem
     * @return
     */
    @Override
    public boolean add(CheckItem checkItem) {
        int row = checkItemDao.add(checkItem);
        return row > 0;
    }

    /**
     * 分页条件查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 模糊查询 拼接 %
        // 判断是否有查询条件
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            // 有查询条件，拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 紧接着的查询语句会被分页
        Page<CheckItem> page = checkItemDao.findPage(queryPageBean.getQueryString());
        // 封装到分页结果对象中
        return new PageResult<CheckItem>(page.getTotal(), page.getResult());
    }

    /**
     * 删除检查项
     *
     * @param id
     */
    @Override
    public void deleteById(int id) throws HealthException {
        //先判断这个检查项是否被检查组使用了
        //调用dao查询检查项的id是否在t_checkgroup_checkitem表中存在记录
        int cnt = checkItemDao.findCountByCheckItemId(id);
        //被使用了则不能删除
        if (cnt > 0) {
            // 已经被检查组使用了，则不能删除，报自定义异常错误
            throw new HealthException(MessageConstant.CHECKITEM_IN_USE);
        }
        //没使用就可以调用dao删除
        checkItemDao.deleteById(id);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    /**
     * 编辑检查项
     *
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
}
