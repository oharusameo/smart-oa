package com.quxue.template.domain.dto;

import com.quxue.template.common.enums.SignTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("打卡的请求对象")
public class SignDTO {


    @ApiModelProperty(value = "打卡时GPS定位地址", example = "广东省广州市越秀区")
    private String address;
    @ApiModelProperty(value = "打卡类型",example = "SIGN_IN")
    private SignTypeEnum signType;
}
