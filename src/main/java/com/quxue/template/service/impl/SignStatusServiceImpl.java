package com.quxue.template.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quxue.template.common.constant.SignConst;
import com.quxue.template.common.utils.TokenUtils;
import com.quxue.template.domain.dto.GetSignStatDTO;
import com.quxue.template.domain.pojo.User;
import com.quxue.template.domain.vo.DateVo;
import com.quxue.template.domain.vo.SignStatVo;
import com.quxue.template.exception.BusinessException;
import com.quxue.template.mapper.HolidaysMapper;
import com.quxue.template.mapper.SignDetailMapper;
import com.quxue.template.mapper.UserMapper;
import com.quxue.template.mapper.WorkdayMapper;
import com.quxue.template.service.SignStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SignStatusServiceImpl implements SignStatusService, SignConst {
    //        @ApiModelProperty("类型：1：签到；0：签退")
//        private SignTypeEnum signType;
//
//        @ApiModelProperty("结果 1:正常 2:迟到 3:早退")
//        private SignStatusEnum signStatus;
    @Resource
    private TokenUtils tokenUtils;
    @Resource
    private UserMapper userMapper;
    @Resource
    private SignDetailMapper signDetailMapper;

    @Resource
    private HolidaysMapper holidaysMapper;
    @Resource
    private WorkdayMapper workdayMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public SignStatVo getSignStat(GetSignStatDTO signStatDTO) {
        String id = tokenUtils.getUserIdFromHeader();
        Date hiredate = userMapper.selectOne(new QueryWrapper<User>().eq("id", id).select("hiredate")).getHiredate();
        DateTime hireDate = DateUtil.parse(hiredate.toString());
        //获取当前查询月份的第一天
        DateTime startDate = DateUtil.parse(signStatDTO.getYear() + "-" + signStatDTO.getMonth() + "-01");
        if (startDate.isBefore(DateUtil.beginOfMonth(hireDate))) {
            throw new BusinessException("当前员工尚未入职，无考勤数据");
        }
        if (startDate.month() == DateUtil.month(hireDate)) {
            startDate = hireDate;
        }

        DateTime endDate = DateUtil.endOfMonth(startDate);

        List<DateVo> signStatistics = signDetailMapper.getSignStatistics(id, startDate, endDate);


        List<String> workdays = workdayMapper.getWorkdaysInRange(startDate, endDate);
        List<String> holidays = holidaysMapper.getHolidaysInRange(startDate, endDate);

        DateRange range = DateUtil.range(startDate, endDate, DateField.DAY_OF_MONTH);
        List<DateVo> list = new ArrayList<>();
        SignStatVo signStatVo = new SignStatVo();
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        for (DateTime d : range) {
            String type = WORKDAY;
            String date = d.toString("yyyy-MM-dd");
            if (d.isWeekend()) {
                type = HOLIDAY;
            }
            if (workdays != null && workdays.contains(date)) {
                type = WORKDAY;
            } else if (holidays != null && holidays.contains(date)) {
                type = HOLIDAY;
            }

            String status = "";
            if (WORKDAY.equals(type) && DateUtil.compare(d, DateUtil.date()) <= 0) {
                status = ABSENCE;
                for (DateVo dateVo : signStatistics) {
                    if (DateUtil.compare(dateVo.getDate(), d) == 0) {
                        if (DateUtil.today().compareTo(date) == 0) {
                            String statusInRedis = opsForValue.get(String.format("sign:userId:%s", id));
                            if (statusInRedis != null) {
                                status = statusInRedis.toLowerCase();
                                break;
                            }
                        }

                        if (dateVo.getStatus() != null) {//如果查出来没有status，则认为是缺勤
                            status = dateVo.getStatus();
                            break;
                        }
                    }
                }
            }
            DateVo dateVo = new DateVo();
            dateVo.setStatus(status);
            dateVo.setType(type);
            dateVo.setDay(d.dayOfWeekEnum().toChinese("周"));
            dateVo.setDate(d);
            list.add(dateVo);
        }
        int absence = 0, normal = 0, abnormal = 0;
        for (DateVo dateVo : list) {
            switch (dateVo.getStatus()) {
                case ABSENCE: {
                    absence++;
                    break;
                }
                case ABNORMAL: {
                    abnormal++;
                    break;
                }
                case NORMAL: {
                    normal++;
                    break;
                }
            }
        }


        signStatVo.setList(list);
        signStatVo.setAbsence(absence);
        signStatVo.setNormal(normal);
        signStatVo.setAbnormal(abnormal);
        return signStatVo;
    }
}
