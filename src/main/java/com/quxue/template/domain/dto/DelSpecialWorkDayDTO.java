package com.quxue.template.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class DelSpecialWorkDayDTO {
    @NotNull
    @ApiModelProperty("特殊工作日id")
    private Integer workdayId;
}
