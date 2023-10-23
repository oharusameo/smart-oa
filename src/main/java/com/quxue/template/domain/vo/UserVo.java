package com.quxue.template.domain.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

import static org.yaml.snakeyaml.tokens.Token.ID.Value;

@Data
@ApiModel("返回用户数据对象")
@NoArgsConstructor
public class UserVo {
    @ApiModelProperty("姓名")
    private String username;
    @ApiModelProperty("手机号码")
    private String telephone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("入职日期")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date hiredate;
    @ApiModelProperty("部门名称")
    private String department;
    @ApiModelProperty("岗位")
    private String station;
    @ApiModelProperty("是否为超级管理员，1：管理员，0：非管理员")
    private Integer root;
//    @ApiModelProperty("员工状态，1：正常 2：冻结，3：离职")
//    private Integer status;
}
