package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.RoleDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    //分页查询所有角色
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //// 判断是否有查询条件
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            //有查询条件，拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //紧接着的查询语句会被分页
        Page<Role> page = roleDao.findPage(queryPageBean.getQueryString());
        PageResult<Role> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return pageResult;
    }

    //查询所有权限
    @Override
    public List<Permission> findAllPermission() {
        return roleDao.findAllPermission();
    }

    //查询菜单
    @Override
    public List<Menu> findAllMenu() {
        return roleDao.findAllMenu();
    }

    //添加角色
    @Override
    @Transactional
    public void roleAdd(Integer[] menuIds, Integer[] permissionIds, Role role) {
        //添加角色
        roleDao.roleAdd(role);
        //获取角色id
        Integer roleId = role.getId();
        if (permissionIds != null) {
            //添加权限与角色的关系
            for (Integer permissionId : permissionIds) {
                roleDao.addPermissionWithRole(permissionId,roleId);
            }
        }
        if (menuIds != null) {
            //添加菜单与角色的关系
            for (Integer menuId : menuIds) {
                roleDao.addMenuWithRole(menuId,roleId);
            }
        }
    }

    //通过角色id回显角色信息
    @Override
    public Role selectRoleByRoleId(Integer id) {
        return roleDao.selectRoleByRoleId(id);
    }

    //通过角色id回显菜单id
    @Override
    public List<Integer> selectMenuIdByRoleId(Integer id) {
        return roleDao.selectMenuIdByRoleId(id);
    }

    //通过角色id回显权限id
    @Override
    public List<Integer> selectPermissionIdByRoleId(Integer id) {
        return roleDao.selectPermissionIdByRoleId(id);
    }

    //编辑角色
    @Override
    @Transactional
    public void roleEdit(Integer[] menuIds, Integer[] permissionIds, Role role) {
        //修改角色信息
        roleDao.updateRole(role);
        //获取角色id
        Integer roleId = role.getId();
        //删除角色与权限的旧关系
        roleDao.deleteRoleWithPermission(roleId);
        //添加角色与权限的关系
        if (permissionIds != null){
            for (Integer permissionId : permissionIds) {
                roleDao.addPermissionWithRole(permissionId,roleId);
            }
        }
        //删除角色与菜单的旧关系
        roleDao.deleteRoleWithMenu(roleId);
        //添加角色与权限的关系
        if (permissionIds != null){
            for (Integer menuId : menuIds) {
                roleDao.addMenuWithRole(menuId,roleId);
            }
        }

    }

    //删除角色
    @Override
    @Transactional
    public void roleDelete(Integer id) throws HealthException {
        //判断是否有用户使用该角色
        Integer cnt = roleDao.selectUserByRoleId(id);
        if (cnt > 0){
            //被用户使用
            throw new HealthException("该角色已被用户使用，不能删除");
        }
        //没有被使用
        //1.删除角色和菜单的关系
        roleDao.deleteRoleWithMenu(id);
        //2.删除角色和权限的关系
        roleDao.deleteRoleWithPermission(id);
        //3.删除角色
        roleDao.deleteRoleById(id);

    }
}
