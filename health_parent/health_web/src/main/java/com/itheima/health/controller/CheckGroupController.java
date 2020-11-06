package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    //添加检查组
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        //调用业务层
        checkGroupService.add(checkGroup, checkitemIds);
        //响应结果
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);

    }

    //分页查询检查组
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        //调用业务层
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        //响应结果
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);
    }

    //根据id查询检查组
    @GetMapping("/findById")
    public Result findById(Integer checkGroupId) {
        CheckGroup checkGroup = checkGroupService.findById(checkGroupId);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);

    }

    //根据检查组id查询相关联的检查项id
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(checkGroupId);

        return new Result(true, MessageConstant.QUERY_CHECKITEM_FAIL, checkItemIds);
    }

    //编辑检查组
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        //调用业务层
        checkGroupService.update(checkGroup, checkitemIds);
        //响应结果
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);

    }

    //删除检查组
    @PostMapping("/deleteById")
    public Result deleteById(int id) {
        //调用业务层
        checkGroupService.deleteById(id);
        //响应结果
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    //查询所有检查组
    @GetMapping("/findAll")
    public Result findAll(){
        //调用业务层
        List<CheckGroup> groupList = checkGroupService.findAll();
        //响应结果
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,groupList);

    }


}


















