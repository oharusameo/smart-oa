package com.quxue.template;

import cn.hutool.core.util.RandomUtil;
import com.quxue.template.utils.WeChatUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Random;

@SpringBootTest
class SmartOAApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private WeChatUtils weChatUtils;

    @Test
    public void testStringRedisTemplate() {
    }


}
