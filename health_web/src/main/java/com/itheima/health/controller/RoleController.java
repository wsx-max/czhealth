package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    private RoleService roleService;

    //分页查询角色
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = roleService.findPage(queryPageBean);
        return new Result(true, "分页查询角色成功", pageResult);
    }

    //查询所有权限
    @GetMapping("/findAllPermission")
    public Result findAllPermission() {
        List<Permission> permissionList = roleService.findAllPermission();
        return new Result(true, "查询所有权限成功", permissionList);
    }

    //查询所有菜单
    @GetMapping("/findAllMenu")
    public Result findAllMenu() {
        List<Menu> menuList = roleService.findAllMenu();
        return new Result(true, "查询菜单成功", menuList);
    }

    //添加角色
    @PostMapping("/roleAdd")
    public Result roleAdd(Integer[] menuIds, Integer[] permissionIds, @RequestBody Role role) {
        roleService.roleAdd(menuIds, permissionIds, role);
        return new Result(true, "添加角色成功");
    }

    //通过角色id回显角色信息
    @GetMapping("/selectRoleByRoleId")
    public Result selectRoleByRoleId(Integer id) {
        Role role = roleService.selectRoleByRoleId(id);
        return new Result(true, "查询角色成功", role);
    }

    //通过角色id回显菜单id
    @GetMapping("/selectMenuIdByRoleId")
    public Result selectMenuIdByRoleId(Integer id) {
        List<Integer> menuList = roleService.selectMenuIdByRoleId(id);
        return new Result(true, "查询菜单信息成功", menuList);
    }

    //通过角色id回显权限id
    @GetMapping("/selectPermissionIdByRoleId")
    public Result selectPermissionIdByRoleId(Integer id) {
        List<Integer> permissionList = roleService.selectPermissionIdByRoleId(id);
        return new Result(true, "查询权限成功", permissionList);
    }

    //编辑角色
    @PostMapping("/roleEdit")
    public Result roleEdit(Integer[] menuIds, Integer[] permissionIds, @RequestBody Role role) {
        roleService.roleEdit(menuIds, permissionIds, role);
        return new Result(true, "修改角色成功");
    }

    //删除角色
    @GetMapping("/roleDelete")
    public Result roleDelete(Integer id){
        roleService.roleDelete(id);
        return new Result(true,"删除角色成功");
    }
}
