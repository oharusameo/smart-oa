package com.quxue.template;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quxue.template.common.utils.JWTUtils;
import com.quxue.template.common.utils.WeChatUtils;
import com.quxue.template.domain.pojo.User;
import com.quxue.template.mapper.UserMapper;
import com.quxue.template.task.MonitorTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
class SmartOAApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private WeChatUtils weChatUtils;

    @Autowired
    private JWTUtils jwtUtils;
    @Resource
    private UserMapper userMapper;
    @Resource
    private MonitorTask monitorTask;

    @Test
    public void testDate() {
        Date createTime = userMapper.selectOne(new QueryWrapper<User>().eq("id", 1).select("create_time")).getCreateTime();
        DateTime dateTime = DateUtil.endOfMonth(createTime);
        Date createTime2 = userMapper.selectOne(new QueryWrapper<User>().eq("id", 1).select("create_time")).getCreateTime();
        String format = DateUtil.format(createTime2, "yyyy-MM-dd");
        String dateStr = dateTime.toDateStr();
        int r = format.compareTo(dateStr);
        System.out.println("dateStr = " + dateStr);
        System.out.println("format = " + format);
        System.out.println("r = " + r);
    }

    public static void main(String[] args) {
        DateTime date = DateUtil.date();
        String string = date.toString("HH:ss");
        System.out.println("date = " + date);
    }

    @Test
    public void testStringRedisTemplate() {
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.set("sign:userId:1:","abnormal");

    }

    @Test
    public void test() {

    }


}
