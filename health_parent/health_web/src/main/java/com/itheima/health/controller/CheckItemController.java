package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;


    //查询所有检查项
    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckItem> list = checkItemService.findAll();
        if (list != null) {
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
        } else {
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    //添加检查项
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        boolean flag = checkItemService.add(checkItem);

        return new Result(flag, flag ? MessageConstant.ADD_CHECKITEM_SUCCESS : MessageConstant.ADD_CHECKITEM_FAIL);
    }

    //分页查询检查项
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        if (pageResult != null) {
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
        } else {
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    //删除检查项
    @PostMapping("/deleteById")
    public Result deleteById(Integer id) {
        boolean flag = checkItemService.deleteById(id);

        return new Result(flag, flag ? MessageConstant.DELETE_CHECKITEM_SUCCESS : MessageConstant.DELETE_CHECKGROUP_FAIL);
    }
}











