package com.itheima.health.dao;


import com.github.pagehelper.Page;
import com.itheima.health.pojo.Permission;

/**
 * @author Justice
 * @date 2020/11/6 14:59
 */
public interface PermissionDao {

//分页查询
    Page<Permission> findPage(String queryString);
//添加
    void add(Permission permission);
//删除
    void delete(Integer id);
    //看看有没有被使用
    int findCountByPermission(Integer id);
//编辑1
    Permission update(Integer id);
//编辑2
    void edit(Permission permission);

}
