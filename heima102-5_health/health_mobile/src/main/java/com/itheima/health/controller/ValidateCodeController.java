package com.itheima.health.controller;

import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.utils.SMSUtils;
import com.itheima.health.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("validateCode")
@Slf4j
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("send4Order")
    public Result send4Order(String telephone) {
        //- 从redis取出，如果有值，发送过了。
        String key = RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone;
        Jedis jedis = jedisPool.getResource();
        // 取出redis中的验证码
        String codeInRedis = jedis.get(key);
        if (StringUtils.isEmpty(codeInRedis)) {
            //- 如果没值：
            //  - 生成验证码
            String code = String.valueOf(ValidateCodeUtils.generateValidateCode(6));
            //  - 调用SMSUtils.发送
            try {
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code);
                log.debug("验证码发送成功 手机:{}, 验证码:{}", telephone, code);
            } catch (Exception e) {
                log.error("发送验证码失败", e);
            }
            //  - 存入redis，加入有效时间，过期失效
            jedis.setex(key, 10 * 60, code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } else {
            // 有值，
            return new Result(false, "验证码已经发送过了，请注意查收");
        }
    }

    /**
     * 发送登陆手机验证码
     *
     * @param telephone
     * @return
     */
    @PostMapping("send4Login")
    public Result send4Login(String telephone) {
        //- 生成验证码之前要检查一下是否发送过了, 通过redis获取key为手机号码，看是否存在
        String key = RedisMessageConstant.SENDTYPE_LOGIN + "_" + telephone;
        Jedis jedis = jedisPool.getResource();
        // redis中的验证码
        String codeInRedis = jedis.get(key);
        if (codeInRedis == null) {
            //- 不存在，没发送，生成验证码，调用SMSUtils.发送验证码，把验证码存入redis(5,10,15分钟有效期)，value:验证码, key:手机号码
            Integer code = ValidateCodeUtils.generateValidateCode(6);
            try {
                //发送验证码
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code + "");
                //  - 存入redis，加入有效时间，过期失效
                jedis.setex(key, 10 * 60, code + "");
                // 返回成功
                return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
            } catch (Exception e) {
                log.error("发送验证码失败", e);
                // 发送失败
                return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
            }
        }
        //- 存在：验证码已经发送了，请注意查收
        return new Result(false, "验证码已经发送了，请注意查收");
    }
}
