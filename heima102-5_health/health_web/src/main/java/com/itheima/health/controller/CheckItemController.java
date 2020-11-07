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

/**
 * 体检检查项管理
 */
@RestController
@RequestMapping("checkItem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询检查项
     *
     * @return
     */
    @RequestMapping("findAll")
    public Result findAll() {
        // 调用服务查询
        List<CheckItem> checkItemList = checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkItemList);
    }

    /**
     * 新增检查项
     *
     * @param checkItem 接收前端提交过来的formData
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestBody CheckItem checkItem) {
        // 调用服务添加
        boolean flag = checkItemService.add(checkItem);
        return new Result(flag, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 分页条件查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        // 调用业务来分页，获取分页结果
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        //return pageResult;
        // 返回给页面, 包装到Result, 统一风格
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    /**
     * 删除检查项
     *
     * @param id
     * @return
     */
    @GetMapping("deleteById")
    public Result deleteById(int id) {
        // 调用业务服务
        checkItemService.deleteById(id);
        // 响应结果
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("findById")
    public Result findById(int id) {
        // 调用业务服务
        CheckItem checkItem = checkItemService.findById(id);
        // 响应结果
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
    }

    /**
     * 编辑检查项
     *
     * @param checkItem
     * @return
     */
    @PostMapping("update")
    public Result update(@RequestBody CheckItem checkItem) {
        // 调用业务服务
        checkItemService.update(checkItem);
        // 响应结果
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

}
