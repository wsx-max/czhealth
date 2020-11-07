package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 新增检查组
     *
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    @PostMapping("add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {
        // 调用业务服务
        checkGroupService.add(checkGroup, checkItemIds);
        // 响应结果
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页条件查询
     *
     * @param queryPageBean
     * @return
     */
    @PostMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        // 调用业务查询
        PageResult<CheckGroup> checkGroupPageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroupPageResult);
    }

    /**
     * 通过id获取检查组
     *
     * @param checkGroupId
     * @return
     */
    @GetMapping("findById")
    public Result findById(int checkGroupId) {
        // 调用业务查询
        CheckGroup checkGroup = checkGroupService.findById(checkGroupId);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
    }

    /**
     * 通过检查组id查询选中的检查项id
     *
     * @param checkGroupId
     * @return
     */
    @GetMapping("findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int checkGroupId) {
        // 调用业务查询
        List<Integer> list = checkGroupService.findCheckItemIdsByCheckGroupId(checkGroupId);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
    }

    /**
     * 编辑检查组提交
     *
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    @PostMapping("update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {
        // 调用业务服务
        checkGroupService.update(checkGroup, checkItemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 删除检查组
     *
     * @param id
     * @return
     */
    @GetMapping("deleteById")
    public Result deleteById(int id) {
        // 调用业务服务
        checkGroupService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * 查询所有检查组
     *
     * @return
     */
    @GetMapping("findAll")
    public Result findAll() {
        List<CheckGroup> all = checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, all);
    }


}
