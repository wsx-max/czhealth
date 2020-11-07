package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Justice
 * @date 2020/11/6 14:27
 */
@RestController
@RequestMapping("permission")
public class PermissionController {
    @Reference
    private PermissionService permissionService;
    /**
     * 分页条件查询
     */
    @RequestMapping("findPage")
      public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult =permissionService.findPage(queryPageBean);
        return new Result(true, "查询分页成功", pageResult);
    }
    /**
     * 添加
     */
    @RequestMapping("add")
    public Result add(@RequestBody Permission permission){
      try {
          permissionService.add(permission);
          return new Result(true, "添加权限成功");
      }catch(Exception e){
          e.printStackTrace();
          return new Result(false,"添加权限成功");
      }
    }
    /**
     * 删除
     */
    @GetMapping("delete")
    public Result delete(Integer id) {
        try {// 调用业务服务
            permissionService.delete(id);
            // 响应结果
            return new Result(true, "删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
        }
    /**
     * 编辑
     */
    @GetMapping("update")
    public Result update(Integer id){
        try {
            Permission result = permissionService.update(id);
            return new Result(true, "编辑成功", result);
        }catch(Exception e){
            e.printStackTrace();
            return new Result(false,"回显失败");
        }
    }
    /**
     * 编辑2
     */
    @PostMapping("edit")
    public Result edit(@RequestBody Permission permission){
        try {
        permissionService.edit(permission);
        return new Result(true, "成功");
        }catch(Exception e){
            e.printStackTrace();
            return new Result(false,"更新失败");
        }
    }
}
