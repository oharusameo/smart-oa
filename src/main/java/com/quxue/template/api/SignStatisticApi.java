package com.quxue.template.api;

import com.quxue.template.common.annotation.RequireLogin;
import com.quxue.template.common.enums.SignStatusEnum;
import com.quxue.template.common.enums.SignTypeEnum;
import com.quxue.template.domain.dto.GetSignStatDTO;
import com.quxue.template.domain.pojo.Result;
import com.quxue.template.domain.vo.SignStatVo;
import com.quxue.template.service.SignDetailService;
import com.quxue.template.service.SignStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(tags = "考勤统计模块", value = "SignStatisticApi")
@RequestMapping("/api/v1/signStatistics/")
@RequireLogin
public class SignStatisticApi {

    @Resource
    private SignStatusService signStatusService;

    @PostMapping("/getMonthlySignStatistics")
    @ApiOperation("获取月度考勤统计")
    public Result getMonthlySignStatistics(@ApiParam(name = "token", value = "身份认证令牌")
                                           @RequestHeader String token, @RequestBody @Valid GetSignStatDTO signStatDTO) {
        SignStatVo signStatVo = signStatusService.getSignStat(signStatDTO);
        return Result.success(signStatVo);
    }
}
