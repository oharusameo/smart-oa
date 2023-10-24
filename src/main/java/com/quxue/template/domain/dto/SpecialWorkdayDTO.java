package com.quxue.template.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel
@Data
public class SpecialWorkdayDTO {
    @NotNull
    @ApiModelProperty("特殊工作日")
    private Date specialWorkday;
}
