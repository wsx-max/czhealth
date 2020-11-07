package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    /**
     * 新增检查组
     *
     * @param checkGroup
     * @param checkItemIds
     */
    void add(CheckGroup checkGroup, Integer[] checkItemIds);

    /**
     * 分页条件查询
     *
     * @param queryPageBean
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 通过id获取检查组
     *
     * @param checkGroupId
     * @return
     */
    CheckGroup findById(int checkGroupId);

    /**
     * 通过检查组id查询选中的检查项id
     *
     * @param checkGroupId
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId);

    /**
     * 编辑检查组提交
     *
     * @param checkGroup
     * @param checkItemIds
     */
    void update(CheckGroup checkGroup, Integer[] checkItemIds);

    /**
     * 删除检查组
     *
     * @param id 【注意】这里的异常是我们自己抛出的异常类
     */
    void deleteById(int id) throws HealthException;

    /**
     * 查询所有检查组
     *
     * @return
     */
    List<CheckGroup> findAll();

}
