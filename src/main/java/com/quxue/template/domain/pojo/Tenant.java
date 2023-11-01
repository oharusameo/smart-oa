package com.quxue.template.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.quxue.template.common.enums.TenantEnum;
import lombok.Data;

/**
 * 
 * @TableName t_tenant
 */
@TableName(value ="t_tenant")
@Data
public class Tenant implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 租户名称
     */
    private String name;

    /**
     * 申请日期
     */
    private Date applyDate;

    /**
     * 状态(0:停用,1:正式,2:试用)
     */
    private TenantEnum tStatus;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 到期时间
     */
    private Date maturity;

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
        Tenant other = (Tenant) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getApplyDate() == null ? other.getApplyDate() == null : this.getApplyDate().equals(other.getApplyDate()))
            && (this.getTStatus() == null ? other.getTStatus() == null : this.getTStatus().equals(other.getTStatus()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getApplicant() == null ? other.getApplicant() == null : this.getApplicant().equals(other.getApplicant()))
            && (this.getTelephone() == null ? other.getTelephone() == null : this.getTelephone().equals(other.getTelephone()))
            && (this.getMaturity() == null ? other.getMaturity() == null : this.getMaturity().equals(other.getMaturity()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getApplyDate() == null) ? 0 : getApplyDate().hashCode());
        result = prime * result + ((getTStatus() == null) ? 0 : getTStatus().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getApplicant() == null) ? 0 : getApplicant().hashCode());
        result = prime * result + ((getTelephone() == null) ? 0 : getTelephone().hashCode());
        result = prime * result + ((getMaturity() == null) ? 0 : getMaturity().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", applyDate=").append(applyDate);
        sb.append(", tStatus=").append(tStatus);
        sb.append(", address=").append(address);
        sb.append(", applicant=").append(applicant);
        sb.append(", telephone=").append(telephone);
        sb.append(", maturity=").append(maturity);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}