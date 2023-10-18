package com.quxue.template.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("初始化管理员请求对象")
public class AdminInitDTO {

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    @NotBlank
    private String username;


    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    @NotBlank
    @Pattern(regexp = "^0?(13|15|18|19)[0-9]{9}$", message = "手机格式不符合条件")
    private String telephone;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @NotBlank
    @Email
    private String email;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    @NotBlank
    private String department;

    /**
     * 岗位
     */
    @ApiModelProperty("岗位")
    @NotBlank
    private String station;


}
