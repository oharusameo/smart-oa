package com.quxue.template.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.common.utils.TokenUtils;
import com.quxue.template.domain.dto.SetClockDTO;
import com.quxue.template.domain.pojo.ClockInRule;
import com.quxue.template.exception.BusinessException;
import com.quxue.template.service.ClockInRuleService;
import com.quxue.template.mapper.ClockInRuleMapper;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * @author harusame
 * @description 针对表【t_clock_in_rule】的数据库操作Service实现
 * @createDate 2023-10-17 20:12:16
 */
@Slf4j
@Service
public class ClockInRuleServiceImpl extends ServiceImpl<ClockInRuleMapper, ClockInRule>
        implements ClockInRuleService {

    @Resource
    private ClockInRuleMapper clockInRuleMapper;
    @Resource
    private TokenUtils tokenUtils;

    @Override
    public void set(SetClockDTO setClockDTO) {

        compareDate(setClockDTO);
        ClockInRule clockInRule = new ClockInRule();
        BeanUtils.copyProperties(setClockDTO, clockInRule);
        Integer userId = Integer.valueOf(tokenUtils.getUserIdFromHeader());
        clockInRule.setUserId(userId);
        saveOrUpdate(clockInRule, new QueryWrapper<ClockInRule>().eq("user_id", userId));
    }

    @Override
    public ClockInRule get() {
        String userId = tokenUtils.getUserIdFromHeader();
        ClockInRule clockInRule = clockInRuleMapper.selectOne(new QueryWrapper<ClockInRule>().eq("user_id", userId));
        if (clockInRule == null) {
            throw new BusinessException("当前尚未设置考勤打卡规则");
        }
        return clockInRule;
    }

    private void compareDate(SetClockDTO s) {
        if (s.getSigninTime().compareTo(s.getSigninEndTime()) > 0) {
            throw new BusinessException("正常上班时间不能晚于最晚可上班打卡时间");
        }
        if (s.getSigninTime().compareTo(s.getSigninStartTime()) < 0) {
            throw new BusinessException("正常上班时间不能早于最早可上班打卡时间");
        }
        if (s.getSignoutTime().compareTo(s.getSignoutEndTime()) > 0) {
            throw new BusinessException("正常下班时间不能晚于最晚可下班打卡下时间");
        }
        if (s.getSignoutTime().compareTo(s.getSignoutStartTime()) < 0) {
            throw new BusinessException("正常下班时间不能早于最早可下班打卡时间");
        }
        if (s.getSigninEndTime().compareTo(s.getSignoutStartTime()) >= 0) {
            throw new BusinessException("最晚可上班打卡时间不能晚于最早可下班打卡时间");
        }

/*        ArrayList<String> orderedList = new ArrayList<>();
        ArrayList<String> sortList = new ArrayList<>();
        Collections.addAll(orderedList, s.getSigninStartTime(), s.getSigninTime(), s.getSigninEndTime(), s.getSignoutStartTime(), s.getSignoutTime(), s.getSignoutEndTime());
        Collections.addAll(sortList, s.getSigninStartTime(), s.getSigninTime(), s.getSigninEndTime(), s.getSignoutStartTime(), s.getSignoutTime(), s.getSignoutEndTime());
        sortList.sort(Comparator.naturalOrder());
        log.info(orderedList.toString());
        log.info(sortList.toString());
        if (!orderedList.equals(sortList)) {
            throw new BusinessException("打卡规则设置出错");
        }*/
    }

}




