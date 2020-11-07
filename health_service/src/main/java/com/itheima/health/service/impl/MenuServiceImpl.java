package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> findByUserId(String username) {
        //查询获得所有menu集合
        List<Menu> menuList = menuDao.findByUserId(username);
        //创建一个空的集合
        List<Menu> resultMenu = new ArrayList<>();
        //遍历来把子菜单放入父菜单
        //先判断空值
        if (null == menuList){
            throw new HealthException("用户没有查看菜单的权限");
        }
        for (Menu menu : menuList) {
            //不为空就是子菜单
            if (null == menu.getParentMenuId()) {
                //把父菜单放入结果集
                resultMenu.add(menu);
            }
        }
        //把子菜单放入父菜单
        for (Menu menu : menuList) {
            if (null != menu.getParentMenuId()) {
                for (Menu parentMenu : resultMenu) {
                    if (menu.getParentMenuId() == parentMenu.getId()) {
                        parentMenu.getChildren().add(menu);
                    }
                }
            }
        }
        return resultMenu;
    }
}
