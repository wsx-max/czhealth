package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.SetMeal;
import com.itheima.health.service.SetMealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("setMeal")
public class SetMealController {
    @Reference
    private SetMealService setMealService;

    /**
     * 套餐上传图片
     *
     * @param imgFile
     * @return
     */
    @PostMapping("upload")
    public Result upload(MultipartFile imgFile) throws Exception {
        //- 获取原有图片名称，截取到后缀名
        String originalFilename = imgFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //- 生成唯一文件名，拼接后缀名
        String filename = UUID.randomUUID() + extension;
        //- 调用七牛上传文件
        QiNiuUtils.uploadViaByte(imgFile.getBytes(), filename);
        //- 返回数据给页面
        Map<String, String> map = new HashMap<>();
        map.put("imgName", filename);
        map.put("domain", QiNiuUtils.DOMAIN);
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, map);
    }

    /**
     * 新增套餐
     *
     * @param setMeal
     * @param checkGroupIds
     * @return
     */
    @PostMapping("add")
    public Result add(@RequestBody SetMeal setMeal, Integer[] checkGroupIds) {
        // 调用业务服务添加
        setMealService.add(setMeal, checkGroupIds);
        // 响应结果
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页条件查询
     *
     * @param queryPageBean
     * @return
     */
    @PostMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        // 调用服务分页查询
        PageResult<SetMeal> setMealPageResult = setMealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setMealPageResult);
    }

    /**
     * 通过id查询套餐信息
     *
     * @param id
     * @return
     */
    @GetMapping("findById")
    public Result findById(int id) {
        // 调用服务查询
        SetMeal setMeal = setMealService.findById(id);
        // 前端要显示图片需要全路径
        // 封装到map中，解决图片路径问题
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("setMeal", setMeal); // formData
        resultMap.put("domain", QiNiuUtils.DOMAIN); // domain
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, resultMap);
    }

    /**
     * 通过id查询选中的检查组ids
     *
     * @param id
     * @return
     */
    @GetMapping("findCheckGroupIdsBySetMealId")
    public Result findCheckGroupIdsBySetMealId(int id) {
        List<Integer> list = setMealService.findCheckGroupIdsBySetMealId(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
    }

    /**
     * 编辑体检套餐
     *
     * @param setMeal
     * @param checkGroupIds
     * @return
     */
    @PostMapping("update")
    public Result update(@RequestBody SetMeal setMeal, Integer[] checkGroupIds) {
        setMealService.update(setMeal, checkGroupIds);
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    /**
     * 删除套餐
     *
     * @param id
     * @return
     */
    @GetMapping("deleteById")
    @PreAuthorize("hasAuthority('SETMEAL_DELETE')")
    public Result deleteById(int id) throws HealthException {
        setMealService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
}
