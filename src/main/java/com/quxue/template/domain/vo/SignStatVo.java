package com.quxue.template.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("返回打卡状态数据对象")
public class SignStatVo {
    @ApiModelProperty("正常的天数")
    private Integer normal;
    @ApiModelProperty("不正常的天数")
    private Integer abnormal;
    @ApiModelProperty("缺席")
    private Integer absence;
    private List<DateVo> list;
}
