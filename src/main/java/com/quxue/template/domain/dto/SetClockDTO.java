package com.quxue.template.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class SetClockDTO {
    @NotBlank
    @ApiModelProperty("正常上班时间")
    private String signinTime;
    @NotBlank
    @ApiModelProperty("正常下班时间")
    private String signoutTime;

    @NotBlank
    @ApiModelProperty("最晚可下班打卡时间")
    private String signoutEndTime;
    @NotBlank
    @ApiModelProperty("最早可上班打卡时间")
    private String signinStartTime;
    @NotBlank
    @ApiModelProperty("最晚可上班打卡时间")
    private String signinEndTime;

    @NotBlank
    @ApiModelProperty("最早可下班打卡时间")
    private String signoutStartTime;


}
