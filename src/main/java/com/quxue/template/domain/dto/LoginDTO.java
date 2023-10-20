package com.quxue.template.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("登录请求对象")
@Data
public class LoginDTO {
    @NotBlank
    @ApiModelProperty("微信临时授权码")
    private String weixinCode;
}
