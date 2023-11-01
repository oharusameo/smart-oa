package com.quxue.template.api;

import com.quxue.template.common.annotation.RequireRoot;
import com.quxue.template.domain.dto.CreateTenantDto;
import com.quxue.template.domain.pojo.Result;
import com.quxue.template.service.TenantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "租户管理", value = "TenantApi")
@RestController
@RequestMapping("/api/v1/tenant")
public class TenantApi {
    @Resource
    private TenantService tenantService;

    @PostMapping("/createTrialTenant")
    @ApiOperation("新增试用租户")
    public Result createTrialTenant(@RequestBody @Valid CreateTenantDto createTenantDto) {
        String code = tenantService.createTrialTenant(createTenantDto);
        return Result.success("创建租户成功", code);
    }


}
