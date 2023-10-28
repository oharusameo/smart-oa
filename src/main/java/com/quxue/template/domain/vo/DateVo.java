package com.quxue.template.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("返回日期数据对象")
public class DateVo {
    @ApiModelProperty(value = "日期", example = "2023-10-08")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date date;
    @ApiModelProperty(value = "日期类型", example = "工作日")
    private String type;
    @ApiModelProperty(value = "星期",example = "星期一")
    private String day;
    @ApiModelProperty(value = "签到状态",example = "1")
    private String status;

}
