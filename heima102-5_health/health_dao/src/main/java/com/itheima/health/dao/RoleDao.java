package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Role;

public interface RoleDao {
    Page<Role> findPage(String queryString);
}
