package com.quxue.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.common.utils.TokenUtils;
import com.quxue.template.domain.dto.AttendanceAlarmDTO;
import com.quxue.template.domain.pojo.AttendanceAlarm;
import com.quxue.template.exception.BusinessException;
import com.quxue.template.service.AttendanceAlarmService;
import com.quxue.template.mapper.AttendanceAlarmMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author harusame
 * @description 针对表【t_attendance_alarm】的数据库操作Service实现
 * @createDate 2023-10-17 20:12:16
 */
@Service
public class AttendanceAlarmServiceImpl extends ServiceImpl<AttendanceAlarmMapper, AttendanceAlarm>
        implements AttendanceAlarmService {
    @Resource
    private TokenUtils tokenUtils;

    @Resource
    private AttendanceAlarmMapper attendanceAlarmMapper;

    @Override
    public void setAlarm(AttendanceAlarmDTO alarmDTO) {
        AttendanceAlarm attendanceAlarm = new AttendanceAlarm();
        BeanUtils.copyProperties(alarmDTO, attendanceAlarm);
        Integer userId = Integer.valueOf(tokenUtils.getUserIdFromHeader());
        Integer tenantId = Integer.valueOf(tokenUtils.getTenantIdFromHeader());
        attendanceAlarm.setTenantId(tenantId);
        attendanceAlarm.setUserId(userId);
        saveOrUpdate(attendanceAlarm, new QueryWrapper<AttendanceAlarm>().eq("user_id", userId).eq("tenant_id", tenantId));
    }

    @Override
    public AttendanceAlarm getAlarm() {
        String userId = tokenUtils.getUserIdFromHeader();
        String tenantId = tokenUtils.getTenantIdFromHeader();
        AttendanceAlarm attendanceAlarm = attendanceAlarmMapper.selectOne(new QueryWrapper<AttendanceAlarm>().eq("user_id", userId).eq("tenant_id", tenantId));
        if (attendanceAlarm == null) {
            throw new BusinessException("当前尚未设置考勤告警规则");
        }
        return attendanceAlarm;
    }
}




