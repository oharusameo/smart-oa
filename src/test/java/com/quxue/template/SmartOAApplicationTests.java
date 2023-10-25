package com.quxue.template;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.quxue.template.common.utils.JWTUtils;
import com.quxue.template.common.utils.WeChatUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;

@SpringBootTest
class SmartOAApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private WeChatUtils weChatUtils;

    @Autowired
    private JWTUtils jwtUtils;

    @Test
    public void testDate() {
        String s1 = "23:00";
        String s2 = "16:00";
        int r = s1.compareTo(s2);
        System.out.println("r = " + r);

    }

    public static void main(String[] args) {

        String format = DateUtil.format(new Date(), "HH:mm");
    }

    @Test
    public void testStringRedisTemplate() {

    }

    @Test
    public void test() {

    }


}
