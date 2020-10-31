package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
import com.itheima.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderMobileController {

    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    //预约订单
    @PostMapping("/submit")
    @Transactional
    public Result submit(@RequestBody Map<String,String> paraMap){
        //验证码校验
        Jedis jedis = jedisPool.getResource();
        //获取手机号码
        String telephone = paraMap.get("telephone");
        // 验证码的 key
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        String codeInRedis = jedis.get(key);
        if (StringUtils.isEmpty(codeInRedis)){
            //没有值，重新发送
            return new Result(false,"请重新获取验证码");
        }
        //前端传来的验证码
        String validateCode = paraMap.get("validateCode");
        if (!codeInRedis.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //移除redis，防止重复提交
        jedis.del(key);
        // 设置预约的类型
        paraMap.put("orderType", Order.ORDERTYPE_WEIXIN);
        // 预约成功页面展示时需要用到id
        Order order = orderService.submitOrder(paraMap);
        return new Result(true, MessageConstant.ORDER_SUCCESS, order);
    }

    //显示预约成功页面信息
    @PostMapping("/findById")
    public Result findById(int id){
        // 调用服务通过id查询订单信息
        Map<String,Object> orderInfo = orderService.findOrderDetailById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);
    }
}



















