package com.quxue.template.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class CreateTenantDto {
    @ApiModelProperty("租户公司名")
    @NotBlank
    private String name;
    @ApiModelProperty("地址")
    @NotBlank
    private String address;
    @ApiModelProperty("联系电话")
    @NotBlank
    private String telephone;
    @ApiModelProperty("申请人")
    @NotBlank
    private String applicant;
    @ApiModelProperty("邮箱")
    @NotBlank
    @Email
    private String email;
}
