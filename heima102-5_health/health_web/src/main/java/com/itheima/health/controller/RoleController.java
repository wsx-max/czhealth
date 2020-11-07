package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Permission;
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
//    @GetMapping("/findAllPermission")
//    public Result findAllPermission() {
//        List<Permission> permissionList = roleService.findAllPermission();
//        return new Result(true, "查询所有权限成功", permissionList);
//    }
}
