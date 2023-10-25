package com.quxue.template.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.common.constant.SignConst;
import com.quxue.template.common.utils.TokenUtils;
import com.quxue.template.domain.pojo.ClockInRule;
import com.quxue.template.domain.pojo.SignDetail;
import com.quxue.template.exception.BusinessException;
import com.quxue.template.mapper.*;
import com.quxue.template.service.SignDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author harusame
 * @description 针对表【t_sign_detail】的数据库操作Service实现
 * @createDate 2023-10-17 20:12:16
 */
@Service
public class SignDetailServiceImpl extends ServiceImpl<SignDetailMapper, SignDetail>
        implements SignDetailService, SignConst {

    @Resource
    private HolidaysMapper holidaysMapper;

    @Resource
    private WorkdayMapper workdayMapper;
    @Resource
    private ClockInRuleMapper clockInRuleMapper;
    @Resource
    private SignDetailMapper signDetailMapper;
    @Resource
    private TokenUtils tokenUtils;
    @Resource
    private UserMapper userMapper;

    @Override
    public void validCanSignIn() {
        validCanSign(SIGN_IN);
    }

    @Override
    public void validCanSignOut() {
        validCanSign(SIGN_OUT);
    }

    private void validCanSign(Boolean signStatus) {
        DateTime date = DateUtil.date();
        IsWorkDay(date);
        String userId = tokenUtils.getUserIdFromHeader();
        String formattedDate = DateUtil.format(date, "HH:mm");
        IsInTime(userId, formattedDate, signStatus);
        //判断当天是否已打卡
        if (signDetailMapper.selectSignInRecord(userId) != null) {
            throw new BusinessException("今天已经打过卡了");
        }
    }

    /**
     * 判断是否为需要上班
     */
    private void IsWorkDay(DateTime date) {
        boolean work = !date.isWeekend();
        if (holidaysMapper.isTodayHoliday() != null) {
            //判断是否为特殊节假日
            work = false;
        } else if (workdayMapper.isTodayWorkDay() != null) {
            //判断是否为特殊工作日
            work = true;
        }
        if (!work) {
            throw new BusinessException("今天休息，不用上班");
        }
    }

    /**
     * 判断是否在合法打卡时间内
     */
    protected void IsInTime(String userId, String date, boolean signStatus) {
        String leaderId = userMapper.selectUpdateUserById(userId);
        //查找当前用户的更新用户id，从而找到当前用户的领导设置的打卡时间
        ClockInRule clockInRule;
        if (leaderId == null) {//如果领导id为空，则认为是root用户
            clockInRule = clockInRuleMapper.selectOne(new QueryWrapper<ClockInRule>().eq("user_id", userId));
        } else {
            clockInRule = clockInRuleMapper.selectOne(new QueryWrapper<ClockInRule>().eq("user_id", leaderId));
        }
        String start;
        String end;
        if (signStatus) {
            start = clockInRule.getSigninStartTime();
            end = clockInRule.getSigninEndTime();
        } else {
            start = clockInRule.getSignoutStartTime();
            end = clockInRule.getSignoutEndTime();
        }

        if (date.compareTo(start) < 0) {
            throw new BusinessException("还没到打卡时间，你先别急");
        } else if (date.compareTo(end) > 0) {
            throw new BusinessException("已经过了打卡时间，寄");
        }
    }

}




