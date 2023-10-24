package com.quxue.template.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class AttendanceAlarmDTO {
    @ApiModelProperty("早退次数")
    @NotNull
    private Integer earlyCount;
    @ApiModelProperty("迟到次数")
    @NotNull
    private Integer lateCount;

}
