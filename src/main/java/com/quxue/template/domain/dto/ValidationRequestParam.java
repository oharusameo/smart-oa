package com.quxue.template.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel
public class ValidationRequestParam {
    @NotBlank
    @ApiModelProperty
    @Pattern(regexp = "^0?(13|15|18)[0-9]{9}$",message = "手机格式不符合条件")
    private String phone;
}
