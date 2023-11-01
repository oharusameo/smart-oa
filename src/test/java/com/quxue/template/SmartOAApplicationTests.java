package com.quxue.template;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quxue.template.common.enums.TenantEnum;
import com.quxue.template.common.utils.JWTUtils;
import com.quxue.template.common.utils.WeChatUtils;
import com.quxue.template.domain.pojo.ClockInRule;
import com.quxue.template.domain.pojo.Tenant;
import com.quxue.template.domain.pojo.User;
import com.quxue.template.mapper.ClockInRuleMapper;
import com.quxue.template.mapper.TenantMapper;
import com.quxue.template.mapper.UserMapper;
import com.quxue.template.service.TenantService;
import com.quxue.template.task.MonitorTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.*;

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
    @Resource
    private ClockInRuleMapper clockInRuleMapper;
    @Resource
    private TenantMapper tenantMapper;
    @Resource
    private TenantService tenantService;

    @Test
    public void testDate() {
        List<Integer> expireTenant = tenantMapper.selectExpireTenant();
        if (expireTenant != null) {
            ArrayList<Tenant> expireTenantList = new ArrayList<>();
            for (Integer id : expireTenant) {
                Tenant tenant = new Tenant();
                tenant.setId(id);
                tenant.setTStatus(TenantEnum.STOP);
                expireTenantList.add(tenant);
            }
            tenantService.updateBatchById(expireTenantList);
        }


    }

    public static void main(String[] args) {
        String userStatus = "1";
        String tenantStatus = "1";

        System.out.println(!("0".equals(userStatus) || "0".equals(tenantStatus)));
    }

    @Test
    public void testStringRedisTemplate() {
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
//        opsForValue.set("sign:userId:1:", "abnormal");


    }

    @Test
    public void test() {
        HashOperations<String, Object, Object> opsForHash = stringRedisTemplate.opsForHash();
        Map<String, Object> map = new HashMap<>();

        ClockInRule clockInRule = clockInRuleMapper.selectOne(new QueryWrapper<ClockInRule>().eq("user_Id", 1));
        System.out.println("clockInRule = " + clockInRule);

//        map.put()
//        opsForHash.putAll("sign:clock:", );
    }


}
