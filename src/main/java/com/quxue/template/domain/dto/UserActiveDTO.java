package com.quxue.template.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class UserActiveDTO {
    @NotBlank
    @ApiModelProperty("注册后发送至邮箱的验证码")
    private String registerCode;

    @NotBlank
    @ApiModelProperty("微信发送的临时验证码")
    private String tempCAPTCHA;
}
