package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Permission;

/**
 * @author Justice
 * @date 2020/11/6 14:54
 */
public interface PermissionService {
    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<Permission> findPage(QueryPageBean queryPageBean);

    /**
     * 新增
     * @param permission
     * @return
     */
    void add(Permission permission);

    /**
     * 删除
     * @param id
     */
    void delete(Integer id);

    /**
     * 编辑
     * @param id
     */
    Permission update(Integer id);

    /**
     * 编辑2
     * @param permission
     */
    void edit(Permission permission);
}
