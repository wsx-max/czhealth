package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;

import java.util.List;

public interface RoleService {

    PageResult findPage(QueryPageBean queryPageBean);

    List<Permission> findAllPermission();

    List<Menu> findAllMenu();

    void roleAdd(Integer[] menuIds, Integer[] permissionIds, Role role);

    Role selectRoleByRoleId(Integer id);

    List<Integer> selectMenuIdByRoleId(Integer id);

    List<Integer> selectPermissionIdByRoleId(Integer id);

    void roleEdit(Integer[] menuIds, Integer[] permissionIds, Role role);

    void roleDelete(Integer id) throws HealthException;
}
