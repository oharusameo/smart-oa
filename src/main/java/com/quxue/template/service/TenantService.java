package com.quxue.template.service;

import com.quxue.template.domain.dto.CreateTenantDto;
import com.quxue.template.domain.pojo.Tenant;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ggzst
* @description 针对表【t_tenant】的数据库操作Service
* @createDate 2023-10-31 11:27:04
*/
public interface TenantService extends IService<Tenant> {

    String createTrialTenant(CreateTenantDto createTenantDto);
}
