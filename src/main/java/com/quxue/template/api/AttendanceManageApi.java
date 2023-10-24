package com.quxue.template.api;

import com.quxue.template.common.annotation.RequireRoot;
import com.quxue.template.domain.dto.*;
import com.quxue.template.domain.pojo.AttendanceAlarm;
import com.quxue.template.domain.pojo.ClockInRule;
import com.quxue.template.domain.pojo.Result;
import com.quxue.template.service.AttendanceAlarmService;
import com.quxue.template.service.ClockInRuleService;
import com.quxue.template.service.HolidaysService;
import com.quxue.template.service.WorkdayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/attendance")
@Api(tags = "考勤管理模块(超级管理员)", value = "AttendanceManageApi")
@RequireRoot
public class AttendanceManageApi {
    @Resource
    private HolidaysService holidaysService;
    @Resource
    private ClockInRuleService clockInRuleService;

    @Resource
    private AttendanceAlarmService attendanceAlarmService;
    @Resource
    private WorkdayService workdayService;

    @PostMapping("/addSpecialHoliday")
    @ApiOperation("增加特殊节假日")
    public Result addSpecialHoliday(@ApiParam(name = "token", value = "身份认证令牌")
                                    @RequestHeader String token, @RequestBody SpecialDayDTO specialDayDTO) {
        holidaysService.addSpecialHoliday(specialDayDTO);
        return Result.successMsg("增加特殊节假日成功");
    }

    @DeleteMapping("/delSpecialHoliday")
    @ApiOperation("删除特殊节假日")
    public Result delSpecialHoliday(@ApiParam(name = "token", value = "身份认证令牌")
                                    @RequestHeader String token, @ApiParam(name = "节假日id", value = "holidayId")
                                    @RequestBody @Valid DelSpecialHolidayDTO delSpecialHoliDayDTO) {
        holidaysService.removeById(delSpecialHoliDayDTO.getHolidayId());
        return Result.successMsg("删除特殊节假日成功");
    }

    @PostMapping("/getSpecialHolidays")
    @ApiOperation("获取特殊节假日列表")
    public Result getSpecialHolidays(@ApiParam(name = "token", value = "身份认证令牌")
                                     @RequestHeader String token) {
        return Result.success(holidaysService.list());
    }

    @PostMapping("/setClockInRule")
    @ApiOperation("设置考勤打卡规则")
    public Result setClockInRule(@ApiParam(name = "token", value = "身份认证令牌")
                                 @RequestHeader String token, @RequestBody @Valid SetClockDTO setClockDTO) {
        clockInRuleService.set(setClockDTO);
        return Result.successMsg("考勤规则设置成功");
    }

    @GetMapping("/getClockInRule")
    @ApiOperation("获取考勤打卡规则")
    public Result getClockInRule(@ApiParam(name = "token", value = "身份认证令牌")
                                 @RequestHeader String token) {
        ClockInRule clockInRule = clockInRuleService.get();
        return Result.success(clockInRule);
    }

    @PostMapping("/setAttendanceAlarm")
    @ApiOperation("设置考勤告警规则")
    public Result setAttendanceAlarm(@ApiParam(name = "token", value = "身份认证令牌")
                                     @RequestHeader String token,
                                     @RequestBody @Valid AttendanceAlarmDTO alarmDTO) {
        attendanceAlarmService.setAlarm(alarmDTO);
        return Result.successMsg("系统考勤告警规则-设置成功！");
    }

    @GetMapping("/getAttendanceAlarm")
    @ApiOperation("获取考勤告警规则")
    public Result getAttendanceAlarm(@ApiParam(name = "token", value = "身份认证令牌")
                                     @RequestHeader String token) {
        return Result.success(attendanceAlarmService.getAlarm());
    }

    @PostMapping("/addSpecialWorkday")
    @ApiOperation("新增特殊工作日")
    public Result addSpecialWorkday(@ApiParam(name = "token", value = "身份认证令牌")
                                    @RequestHeader String token, @RequestBody @Valid SpecialWorkdayDTO specialWorkdayDTO) {
        workdayService.addSpecialWorkDay(specialWorkdayDTO);
        return Result.successMsg("新增特殊工作日成功!");
    }


    @DeleteMapping("/delSpecialWorkday")
    @ApiOperation("删除特殊工作日")
    public Result delSpecialWorkday(@ApiParam(name = "token", value = "身份认证令牌")
                                    @RequestHeader String token, @ApiParam(name = "节假日id", value = "holidayId")
                                    @RequestBody @Valid DelSpecialWorkDayDTO delSpecialWorkDayDTO) {
        workdayService.removeById(delSpecialWorkDayDTO.getWorkdayId());
        return Result.successMsg("删除特殊节假日成功");
    }

    @PostMapping("/getSpecialWorkdays")
    @ApiOperation("获取特殊工作日列表")
    public Result getSpecialWorkdays(@ApiParam(name = "token", value = "身份认证令牌")
                                     @RequestHeader String token) {
        return Result.success(workdayService.list());
    }
}
