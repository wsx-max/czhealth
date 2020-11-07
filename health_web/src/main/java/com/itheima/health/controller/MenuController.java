package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MenuService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    @GetMapping("/showMenu4User")
    public Result showMenu4User(){
        //获取登录的用户信息
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Menu> menuList = menuService.findByUserId(loginUser.getUsername());
        return new Result(true,"菜单展示成功",menuList);
    }
}
