package com.quxue.template;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

@SpringBootTest
class SmartOAApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testStringRedisTemplate() {
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        Boolean testKey = opsForValue.setIfAbsent("testKey", "123", Duration.ofMinutes(5));
        System.out.println("testKey = " + testKey);
    }

}
