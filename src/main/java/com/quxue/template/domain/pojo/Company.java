package com.quxue.template.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_company")
@ApiModel
public class Company implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("公司名")
    private String companyName;
}
