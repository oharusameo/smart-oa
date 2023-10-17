package com.quxue.template.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_clock_in_rule
 */
@TableName(value ="t_clock_in_rule")
@Data
public class ClockInRule implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 上班打卡开始时间点
     */
    private String signinStartTime;

    /**
     * 上班时间点
     */
    private String signinTime;

    /**
     * 上班打卡结束时间点
     */
    private String signinEndTime;

    /**
     * 下班打卡开始时间点
     */
    private String signoutStartTime;

    /**
     * 下班时间点
     */
    private String signoutTime;

    /**
     * 下班打卡结束时间点
     */
    private String signoutEndTime;

    /**
     * 管理员ID
     */
    private Integer userId;

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
        ClockInRule other = (ClockInRule) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSigninStartTime() == null ? other.getSigninStartTime() == null : this.getSigninStartTime().equals(other.getSigninStartTime()))
            && (this.getSigninTime() == null ? other.getSigninTime() == null : this.getSigninTime().equals(other.getSigninTime()))
            && (this.getSigninEndTime() == null ? other.getSigninEndTime() == null : this.getSigninEndTime().equals(other.getSigninEndTime()))
            && (this.getSignoutStartTime() == null ? other.getSignoutStartTime() == null : this.getSignoutStartTime().equals(other.getSignoutStartTime()))
            && (this.getSignoutTime() == null ? other.getSignoutTime() == null : this.getSignoutTime().equals(other.getSignoutTime()))
            && (this.getSignoutEndTime() == null ? other.getSignoutEndTime() == null : this.getSignoutEndTime().equals(other.getSignoutEndTime()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSigninStartTime() == null) ? 0 : getSigninStartTime().hashCode());
        result = prime * result + ((getSigninTime() == null) ? 0 : getSigninTime().hashCode());
        result = prime * result + ((getSigninEndTime() == null) ? 0 : getSigninEndTime().hashCode());
        result = prime * result + ((getSignoutStartTime() == null) ? 0 : getSignoutStartTime().hashCode());
        result = prime * result + ((getSignoutTime() == null) ? 0 : getSignoutTime().hashCode());
        result = prime * result + ((getSignoutEndTime() == null) ? 0 : getSignoutEndTime().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", signinStartTime=").append(signinStartTime);
        sb.append(", signinTime=").append(signinTime);
        sb.append(", signinEndTime=").append(signinEndTime);
        sb.append(", signoutStartTime=").append(signoutStartTime);
        sb.append(", signoutTime=").append(signoutTime);
        sb.append(", signoutEndTime=").append(signoutEndTime);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}