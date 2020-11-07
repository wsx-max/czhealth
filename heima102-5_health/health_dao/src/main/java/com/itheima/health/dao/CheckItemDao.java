package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    /**
     * 查询 所有检查项
     *
     * @return
     */
    List<CheckItem> findAll();

    /**
     * 新增检查项
     *
     * @param checkItem
     * @return
     */
    int add(CheckItem checkItem);

    /**
     * 分页条件查询
     *
     * @param queryString
     * @return
     */
    Page<CheckItem> findPage(String queryString);

    /**
     * 检查 检查项是否被检查组使用了
     *
     * @param id
     * @return
     */
    int findCountByCheckItemId(int id);

    /**
     * 通过id删除检查项
     *
     * @param id
     */
    void deleteById(int id);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 编辑检查项
     *
     * @param checkItem
     */
    void update(CheckItem checkItem);
}
