package com.quxue.template.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel
public class SpecialDayDTO {
    @NotNull
    @ApiModelProperty("特殊节假日")
    private Date specialHoliday;
}
