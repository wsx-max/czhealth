package com.itheima.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CleanImgJob {

    @Reference
    private SetmealService setmealService;

    private static final Logger log = LoggerFactory.getLogger(CleanImgJob.class);


//    @Scheduled(fixedDelay = 180000,initialDelay = 3000)
    public void cleanImg(){

        //查询七牛上的所有图片
        List<String> imgIn7Niu = QiNiuUtils.listFile();
        log.debug("imgIn7Niu 有{}张图片要清理", imgIn7Niu.size());
        // 查出数据库中的所有图片
        List<String> imgInDb = setmealService.findImgs();
        log.debug("imgInDb 有{}张图片", imgInDb.size());
        // 7牛的-数据库的 imgIn7Niu剩下的就是要删除的
        imgIn7Niu.removeAll(imgInDb);
        log.debug("有{}张图片 需要删除", imgIn7Niu.size());
        // 把剩下的图片名转成数组
        String[] strings = imgIn7Niu.toArray(new String[]{});
        //删除七牛上的垃圾图片
        try {
            log.info("开始删除七牛上的图片 {}", imgIn7Niu.size());
            QiNiuUtils.removeFiles(strings);
            log.info("删除七牛上的图片 {}  成功===============", imgIn7Niu.size());
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("清理垃圾图片出错了",e);
        }


    }

}
