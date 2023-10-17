package com.quxue.template.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_attendance_alarm
 */
@TableName(value ="t_attendance_alarm")
@Data
public class AttendanceAlarm implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 迟到次数警告阈值
     */
    private Integer lateCount;

    /**
     * 早退次数警告阈值
     */
    private Integer earlyCount;

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
        AttendanceAlarm other = (AttendanceAlarm) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLateCount() == null ? other.getLateCount() == null : this.getLateCount().equals(other.getLateCount()))
            && (this.getEarlyCount() == null ? other.getEarlyCount() == null : this.getEarlyCount().equals(other.getEarlyCount()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLateCount() == null) ? 0 : getLateCount().hashCode());
        result = prime * result + ((getEarlyCount() == null) ? 0 : getEarlyCount().hashCode());
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
        sb.append(", lateCount=").append(lateCount);
        sb.append(", earlyCount=").append(earlyCount);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}