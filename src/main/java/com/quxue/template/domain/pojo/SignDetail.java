package com.quxue.template.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.quxue.template.common.enums.SignStatusEnum;
import com.quxue.template.common.enums.SignTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @TableName t_sign_detail
 */
@TableName(value = "t_sign_detail")
@Data
@ApiModel
public class SignDetail implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Integer userId;

    /**
     * 签到/签退地址
     */
    @ApiModelProperty("签到/签退地址")
    private String address;

    /**
     * 签到/签退日期
     */
    @ApiModelProperty("签到/签退日期")
    private Date signDate;

    /**
     * 签到/签退时间
     */
    @ApiModelProperty("签到/签退时间")
    private Date signTime;

    /**
     * 类型：1：签到；0：签退
     */
    @ApiModelProperty("类型：1：签到；0：签退")
    private SignTypeEnum signType;

    /**
     * 结果 1:正常 2:迟到 3:早退
     */
    @ApiModelProperty("结果 1:正常 2:迟到 3:早退")
    private SignStatusEnum signStatus;
    @ApiModelProperty("租户id")
    private Integer tenantId;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SignDetail other = (SignDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
                && (this.getSignDate() == null ? other.getSignDate() == null : this.getSignDate().equals(other.getSignDate()))
                && (this.getSignTime() == null ? other.getSignTime() == null : this.getSignTime().equals(other.getSignTime()))
                && (this.getSignType() == null ? other.getSignType() == null : this.getSignType().equals(other.getSignType()))
                && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
                && (this.getSignStatus() == null ? other.getSignStatus() == null : this.getSignStatus().equals(other.getSignStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getSignDate() == null) ? 0 : getSignDate().hashCode());
        result = prime * result + ((getSignTime() == null) ? 0 : getSignTime().hashCode());
        result = prime * result + ((getSignType() == null) ? 0 : getSignType().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getSignStatus() == null) ? 0 : getSignStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", address=").append(address);
        sb.append(", signDate=").append(signDate);
        sb.append(", signTime=").append(signTime);
        sb.append(", signType=").append(signType);
        sb.append(", signStatus=").append(signStatus);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}