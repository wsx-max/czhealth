package com.itheima.health.service;

import com.itheima.health.pojo.Member;

import java.util.List;

public interface MemberService {
    /**
     * 通过手机号查询会员是否存在
     *
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 添加会员
     *
     * @param member
     */
    void add(Member member);

    /**
     * 统计过去一年的会员总数
     *
     * @param months
     * @return
     */
    List<Integer> getMemberReport(List<String> months);
}
