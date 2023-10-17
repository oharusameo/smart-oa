package com.quxue.template.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
public class XssReqParam {
    @NotBlank
    @ApiModelProperty("info")
    private String message;
}
