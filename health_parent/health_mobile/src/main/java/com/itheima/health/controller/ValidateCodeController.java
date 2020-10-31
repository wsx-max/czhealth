package com.itheima.health.controller;

import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.utils.SMSUtils;
import com.itheima.health.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;


    //发送手机验证码
    @PostMapping("/send4Order")
    public Result send4Order(String telephone) {

        //- 生成验证码之前要检查一下是否发送过了, 通过redis获取key为手机号码，看是否存在
        Jedis jedis = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        //获取redis中的验证码
        String codeInRedis = jedis.get(key);
        if (null == codeInRedis) {
            //不存在，生成验证码
//            Integer code = ValidateCodeUtils.generateValidateCode(6);
            Integer code = 123456;

            //发送验证码
            try {
//                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code + "");
                //存入redis，有效时间为15分钟
                jedis.setex(key, 15 * 60, code + "");
                //发送成功
                return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                //发送失败
                return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
            }

        }
        //验证码存在
        return new Result(false, MessageConstant.SENT_VALIDATECODE);

    }

    //快速登入，发送手机验证码
    @PostMapping("/send4Login")
    public Result send4Login(String telephone) {
        //- 生成验证码之前要检查一下是否发送过了, 通过redis获取key为手机号码，看是否存在
        Jedis jedis = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        //获取redis中的验证码
        String codeInRedis = jedis.get(key);
        if (null == codeInRedis) {
            //不存在，生成验证码
//            Integer code = ValidateCodeUtils.generateValidateCode(6);
            Integer code = 123456;
            //发送验证码
            try {
//                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code + "");
                //存入redis，有效时间为15分钟
                jedis.setex(key, 15 * 60, code + "");
                //发送成功
                return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                //发送失败
                return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
            }
        }
        //验证码存在
        return new Result(false, MessageConstant.SENT_VALIDATECODE);
    }

}
















