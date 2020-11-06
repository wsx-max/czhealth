package com.itheima.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    //上传图片
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile){
        //获取图片名称，得到后缀名
        String originalFilename = imgFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        //生成唯一文件名，拼接后缀名
        String fileName = UUID.randomUUID() + extension;
        //调用七牛上传文件

        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),fileName);

            Map<String,String> map = new HashMap<>();
            map.put("imgName",fileName);
            map.put("domain",QiNiuUtils.DOMAIN);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    //添加套餐
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        //调用业务层
        setmealService.add(setmeal,checkgroupIds);
        //响应
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    //分页查询
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用业务层
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);
        //响应
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);

    }

    //根据id查询套餐
    @GetMapping("/findById")
    public Result findById(int id){
        Setmeal setmeal = setmealService.findById(id);
        // 前端要显示图片需要全路径
        // 封装到map中，解决图片路径问题
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("setmeal", setmeal); // formData
        resultMap.put("domain", QiNiuUtils.DOMAIN); // domain

        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,resultMap);
    }

    // 获取选中的检查组id
    @GetMapping("/findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(int id){
        List<Integer> integerList  = setmealService.findCheckgroupIdsBySetmealId(id);

        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,integerList);
    }

    //编辑检查组
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        setmealService.update(setmeal,checkgroupIds);
        return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    //删除检查组
    @GetMapping("/delete")
    public Result delete(Integer id){
        setmealService.delete(id);
        return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
}























