package com.quxue.template.api;

import com.quxue.template.common.annotation.RequireLogin;
import com.quxue.template.common.enums.SignTypeEnum;
import com.quxue.template.domain.dto.SignDTO;
import com.quxue.template.domain.pojo.Result;
import com.quxue.template.domain.pojo.SignDetail;
import com.quxue.template.service.SignDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/sign")
@Api(tags = "签到签退模块", value = "SignApi")
@RequireLogin
public class SignApi {
    @Resource
    private SignDetailService signDetailService;

    @PostMapping("/sign")
    @ApiOperation("打卡，执行签到或签退")
    public Result sign(@ApiParam(name = "token", value = "身份认证令牌")
                       @RequestHeader String token, SignDTO signDTO,
                       @RequestPart("photo") MultipartFile file) {

        Date sign = signDetailService.sign(signDTO,file);
        return Result.successMsg(sign.toString());
    }


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
