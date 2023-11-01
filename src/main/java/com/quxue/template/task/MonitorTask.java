package com.quxue.template.task;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.quxue.template.common.enums.TenantEnum;
import com.quxue.template.domain.pojo.Tenant;
import com.quxue.template.domain.pojo.User;
import com.quxue.template.mapper.TenantMapper;
import com.quxue.template.mapper.UserMapper;
import com.quxue.template.service.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
@Slf4j
public class MonitorTask {

//    @Scheduled(cron = "0/5 * * * * *")//5s一次
//    public void monitor(){
//        log.info("记录信息");
//    }

    @Resource
    private TenantMapper tenantMapper;
    @Resource
    private TenantService tenantService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private UserMapper userMapper;

    @Scheduled(cron = "0 0 18 * * ?")//每天下班时间点触发
    public void clearSignStatInRedis() {
        Set<String> keys = stringRedisTemplate.keys("sign:*");
        if (!Objects.requireNonNull(keys).isEmpty()) {
            stringRedisTemplate.delete(keys);
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")//每天0点触发
    public void checkTrialTenantIfExpire() {
        List<Integer> expireTenant = tenantMapper.selectExpireTenant();
        if (expireTenant != null) {
            ArrayList<Tenant> expireTenantList = new ArrayList<>();
            for (Integer id : expireTenant) {
                Tenant tenant = new Tenant();
                tenant.setId(id);
                tenant.setTStatus(TenantEnum.STOP);
                expireTenantList.add(tenant);
                User user = new User();
                user.setStatus(0);
                userMapper.update(user, new UpdateWrapper<User>().eq("tenant_id", id));
            }
            tenantService.updateBatchById(expireTenantList);
        }

    }

}
