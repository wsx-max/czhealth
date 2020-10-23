package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    CheckItemService checkItemService;

    @GetMapping("/findAll")
    public Result findAll() {

        List<CheckItem> list = checkItemService.findAll();

        if (list != null) {
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
        } else {
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        boolean flag = checkItemService.add(checkItem);

        return new Result(flag, flag ? MessageConstant.ADD_CHECKITEM_SUCCESS : MessageConstant.ADD_CHECKITEM_FAIL);

    }

}











