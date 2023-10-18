package com.quxue.template.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    @ApiModelProperty("微信唯一认证字符串")
    private String openId;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String username;


    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String telephone;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 入职日期
     */
    @ApiModelProperty("入职日期")
    private Date hiredate;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String department;

    /**
     * 岗位
     */
    @ApiModelProperty("岗位")
    private String station;


    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 修改时间，初始状态与创建时间一致
     */
    @ApiModelProperty("修改时间，初始状态与创建时间一致")
    private Date updateTime;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Integer createUser;

    /**
     * 修改人，初始状态与创建人一致
     */
    @ApiModelProperty("修改人，初始状态与创建人一致")
    private Integer updateUser;
}
