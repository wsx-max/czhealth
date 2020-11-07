package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    /**
     * 添加检查组
     *
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查组与检查项的关系
     *
     * @param checkGroupId 注意要取别名，类型相同
     * @param checkItemId
     */
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId, @Param("checkItemId") Integer checkItemId);

    /**
     * 分页条件查询
     *
     * @param queryString
     * @return
     */
    Page<CheckGroup> findByCondition(String queryString);

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
     */
    void update(CheckGroup checkGroup);

    /**
     * 删除检查组与检查项的关系
     *
     * @param checkGroupId
     */
    void deleteCheckGroupCheckItem(Integer checkGroupId);

    /**
     * 删除检查组
     *
     * @param id
     */
    void deleteById(int id);

    /**
     * 通过检查组id查询是否被套餐使用了
     *
     * @param id
     * @return
     */
    int findSetMealCountByCheckGroupId(int id);

    /**
     * 查询所有检查组
     *
     * @return
     */
    List<CheckGroup> findAll();

}
