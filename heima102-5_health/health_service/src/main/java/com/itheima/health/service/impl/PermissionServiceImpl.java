package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.PermissionDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Justice
 * @date 2020/11/6 14:56
 */
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Permission> findPage(QueryPageBean queryPageBean) {
       //页码与大小
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //判断是否有查询条件 如果有要实现模糊查询
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())){
        queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }

    //条件查询,查询语句会被分页
        Page<Permission> page = permissionDao.findPage(queryPageBean.getQueryString());
        PageResult<Permission> pageResult = new PageResult<Permission>(page.getTotal(),page.getResult());
    return pageResult;
    }

    /**
     * 新增
     * @param permission
     * @return
     */
    @Override
    public void add(Permission permission) {
       permissionDao.add(permission);

    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public void delete(Integer id) throws HealthException{
        int cnt = permissionDao.findCountByPermission(id);
        if (cnt>0) {
            throw new HealthException("被使用啦");

        }
       permissionDao.delete(id);

    }

    /**
     * 编辑
     * @param id
     * @return
     */
    @Override
    public Permission update(Integer id) {

        return permissionDao.update(id);
    }

    /**
     * 编辑2
     * @param permission
     */
    @Override
    public void edit(Permission permission) {
        permissionDao.edit(permission);
    }

}