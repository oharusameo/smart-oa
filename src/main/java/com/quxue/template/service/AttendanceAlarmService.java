package com.quxue.template.service;

import com.quxue.template.domain.dto.AttendanceAlarmDTO;
import com.quxue.template.domain.pojo.AttendanceAlarm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author harusame
* @description 针对表【t_attendance_alarm】的数据库操作Service
* @createDate 2023-10-17 20:12:16
*/
public interface AttendanceAlarmService extends IService<AttendanceAlarm> {

    void setAlarm(AttendanceAlarmDTO alarmDTO);

    AttendanceAlarm getAlarm();
}
