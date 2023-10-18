package com.quxue.template.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@ApiModel("发送邮件请求对象")
@Data
public class EmailDTO {
    @NotBlank
    @ApiModelProperty("邮件主题")
    private String subject;

    @NotBlank
    @ApiModelProperty("邮件内容")
    private String message;

    @NotBlank
    @ApiModelProperty("收件人")
    @Email
    private String target;

}
