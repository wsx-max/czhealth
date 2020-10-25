package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId,@Param("checkitemId") Integer checkitemId);

    Page<CheckGroup> findByCondition(String queryString);

    CheckGroup findById(Integer checkGroupId);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    void update(CheckGroup checkGroup);

    void deleteCheckGroupCheckItem(Integer id);

    int findCountByCheckGroupId(int id);

    void deleteById (int id);
}
