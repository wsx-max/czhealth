package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    @PostMapping("/check")
    public Result check(@RequestBody Map<String,String> loginInfo, HttpServletResponse res){
        //验证码校验
        Jedis jedis = jedisPool.getResource();
        //获取手机号码
        String telephone = loginInfo.get("telephone");
        // 验证码的 key
        String key = RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        String codeInRedis = jedis.get(key);
        if (StringUtils.isEmpty(codeInRedis)){
            //没有值，重新发送
            return new Result(false,"请重新获取验证码");
        }
        //前端传来的验证码
        String validateCode = loginInfo.get("validateCode");
        if (!codeInRedis.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //移除redis，防止重复提交
        jedis.del(key);
       //判断是否是会员
        Member member = memberService.findByTelephone(telephone);
        if(null == member){
            // 会员不存在，添加为新会员
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            member.setRemark("手机快速注册");
            memberService.add(member);
        }
        // 跟踪记录的手机号码，代表着会员
        Cookie cookie = new Cookie("login_member_telephone",telephone);
        cookie.setMaxAge(30*24*60*60); // 存1个月
        cookie.setPath("/"); // 访问的路径 根路径下时 网站的所有路径 都会发送这个cookie
        res.addCookie(cookie);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}

















