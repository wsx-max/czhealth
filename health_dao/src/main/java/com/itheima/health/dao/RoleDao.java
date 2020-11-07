package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao {
    Page<Role> findPage(String queryString);

    List<Permission> findAllPermission();

    List<Menu> findAllMenu();

    void roleAdd(Role role);

    void addPermissionWithRole(@Param("permissionId") Integer permissionId, @Param("roleId") Integer roleId);

    void addMenuWithRole(@Param("menuId") Integer menuId, @Param("roleId") Integer roleId);

    Role selectRoleByRoleId(Integer id);

    List<Integer> selectMenuIdByRoleId(Integer id);

    List<Integer> selectPermissionIdByRoleId(Integer id);

    void updateRole(Role role);

    void deleteRoleWithPermission(Integer roleId);

    void deleteRoleWithMenu(Integer roleId);

    Integer selectUserByRoleId(Integer id);

    void deleteRoleById(Integer id);
}
