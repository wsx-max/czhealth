package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.pojo.Member;
import com.itheima.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    /**
     * 通过手机号码查询会员信息
     * @param telephone
     * @return
     */
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }
    /**
     * 添加会员
     * @param member
     */
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public List<Integer> getMemberReport(List<String> months) {
        //遍历每一个月份，查询每一个月的会员数量
        List<Integer> memberCountList = new ArrayList<>();
        if (months != null){
            for (String month : months) {
                Integer count = memberDao.findMemberCountBeforeDate(month);
                memberCountList.add(count);
            }
        }
        return memberCountList;
    }
}











