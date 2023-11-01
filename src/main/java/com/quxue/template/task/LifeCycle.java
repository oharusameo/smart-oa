package com.quxue.template.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quxue.template.domain.pojo.ClockInRule;
import com.quxue.template.mapper.ClockInRuleMapper;
import org.springframework.context.SmartLifecycle;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class LifeCycle implements SmartLifecycle {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ClockInRuleMapper clockInRuleMapper;


    @Override
    public void start() {


//        Map<String, Object> map = new HashMap<>();
//        ClockInRule clockInRule = clockInRuleMapper.selectOne(new QueryWrapper<ClockInRule>().eq("user_id", 1));
//        map.put("signin_time", clockInRule.getSigninTime());
//        map.put("signin_start_time", clockInRule.getSigninStartTime());
//        map.put("signin_end_time", clockInRule.getSigninEndTime());
//        map.put("signout_start_time", clockInRule.getSignoutStartTime());
//        map.put("signout_end_time", clockInRule.getSignoutEndTime());
//        map.put("signout_time", clockInRule.getSignoutTime());
//        opsForHash.putAll(String.format("sign:clock:%s", 1), map);

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
