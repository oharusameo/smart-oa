package com.quxue.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.common.enums.TenantEnum;
import com.quxue.template.common.utils.AuthContextHolder;
import com.quxue.template.domain.dto.CreateTenantDto;
import com.quxue.template.domain.dto.CreateUserDTO;
import com.quxue.template.domain.pojo.ClockInRule;
import com.quxue.template.domain.pojo.Tenant;
import com.quxue.template.exception.BusinessException;
import com.quxue.template.mapper.ClockInRuleMapper;
import com.quxue.template.service.FaceModelService;
import com.quxue.template.service.TenantService;
import com.quxue.template.mapper.TenantMapper;
import com.quxue.template.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ggzst
 * @description 针对表【t_tenant】的数据库操作Service实现
 * @createDate 2023-10-31 11:27:04
 */
@Service
@Slf4j
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant>
        implements TenantService {
    @Resource
    private TenantMapper tenantMapper;

    @Resource
    private FaceModelService faceModelService;
    @Resource
    private UserService userService;
    @Resource
    private ClockInRuleMapper clockInRuleMapper;


    @Override
    public String createTrialTenant(CreateTenantDto createTenantDto) {
        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(createTenantDto, tenant);
        tenant.setTStatus(TenantEnum.TRIAL);
        if (tenantMapper.insertTrialTenant(tenant) != 1) {
            throw new BusinessException("创建用户失败");
        }
        String code = userService.initAdmin(getInitParam(tenant, createTenantDto));
        faceModelService.createGroup(String.valueOf(tenant.getId()), tenant.getName());
        initClockInRule(tenant.getId());
        return code;
    }


    private CreateUserDTO getInitParam(Tenant tenant, CreateTenantDto createTenantDto) {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setTenantId(tenant.getId());
        createUserDTO.setEmail(createTenantDto.getEmail());
        createUserDTO.setDepartment("");
        createUserDTO.setUsername(String.format("%s管理员", createTenantDto.getName()));
        createUserDTO.setStation("root");
        log.info(createTenantDto.toString());
        return createUserDTO;
    }

    @Async
    @Retryable(recover = "reInit", value = BusinessException.class, maxAttempts = 10, backoff = @Backoff)
    public void initClockInRule(Integer tenantId) {
        ClockInRule clockInRule = buildClockInRule(tenantId);
        if (clockInRuleMapper.insert(clockInRule) != 1) {
            throw new BusinessException(String.format("tenantId=%d 初始化打卡时间失败", tenantId));
        }
        AuthContextHolder.remove();
    }


    @Recover
    private void reInit(BusinessException e, Integer tenantId) {
        ClockInRule clockInRule = buildClockInRule(tenantId);
        if (clockInRuleMapper.insert(clockInRule) != 1) {
            log.error(e.getMessage());
        }
        AuthContextHolder.remove();
    }


    private ClockInRule buildClockInRule(Integer tenantId) {
        ClockInRule clockInRule = new ClockInRule();
        clockInRule.setTenantId(tenantId);
        clockInRule.setUserId(AuthContextHolder.getUserId());
        clockInRule.setSigninStartTime("08:00");
        clockInRule.setSigninTime("09:00");
        clockInRule.setSigninEndTime("09:30");
        clockInRule.setSignoutStartTime("17:00");
        clockInRule.setSignoutTime("18:00");
        clockInRule.setSignoutEndTime("19:00");
        return clockInRule;
    }


}




