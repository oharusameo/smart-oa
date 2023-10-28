package com.quxue.template.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@ApiModel
@Data
public class GetSignStatDTO {
    @NotNull
    @ApiModelProperty(value = "查询月份", example = "12")
    @Range(min = 1, max = 12)
    private Integer month;
    @NotNull
    @ApiModelProperty(value = "查询年份", example = "2023")
    @Range(min = 2000, max = 2100)
    private Integer year;
}
