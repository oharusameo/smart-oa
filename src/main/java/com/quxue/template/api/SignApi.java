package com.quxue.template.api;

import com.quxue.template.common.annotation.RequireLogin;
import com.quxue.template.domain.pojo.Result;
import com.quxue.template.service.SignDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/sign")
@Api(tags = "签到签退模块", value = "SignApi")
@RequireLogin
public class SignApi {
    @Resource
    private SignDetailService signDetailService;


    @GetMapping("/validCanSignIn")
    @ApiOperation("检查是否可以签到")
    public Result validCanSignIn(@ApiParam(name = "token", value = "身份认证令牌")
                                 @RequestHeader String token) {
        signDetailService.validCanSignIn();
        return Result.successMsg("可以打卡");
    }

    @GetMapping("/validCanSignOut")
    @ApiOperation("检查是否可以签退")
    public Result validCanSignOut(@ApiParam(name = "token", value = "身份认证令牌")
                                  @RequestHeader String token) {
        signDetailService.validCanSignOut();
        return Result.successMsg("可以下班");
    }
}
