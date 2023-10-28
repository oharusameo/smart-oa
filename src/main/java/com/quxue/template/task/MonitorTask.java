package com.quxue.template.task;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
@Slf4j
public class MonitorTask {

//    @Scheduled(cron = "0/5 * * * * *")//5s一次
//    public void monitor(){
//        log.info("记录信息");
//    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Scheduled(cron = "0 0 18 * * ?")//每天下班时间点触发
    public void clearSignStatInRedis() {
        Set<String> keys = stringRedisTemplate.keys("sign:*");
        if (!Objects.requireNonNull(keys).isEmpty()) {
            stringRedisTemplate.delete(keys);
        }
    }
}
