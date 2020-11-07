package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;

public interface RoleService {
    PageResult findPage(QueryPageBean queryPageBean);
}
