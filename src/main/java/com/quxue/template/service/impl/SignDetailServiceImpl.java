package com.quxue.template.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.common.constant.FaceConst;
import com.quxue.template.common.constant.SignConst;
import com.quxue.template.common.enums.SignStatusEnum;
import com.quxue.template.common.enums.SignTypeEnum;
import com.quxue.template.common.utils.TokenUtils;
import com.quxue.template.domain.dto.SignDTO;
import com.quxue.template.domain.pojo.ClockInRule;
import com.quxue.template.domain.pojo.FaceModel;
import com.quxue.template.domain.pojo.SignDetail;
import com.quxue.template.exception.BusinessException;
import com.quxue.template.mapper.*;
import com.quxue.template.service.FaceModelService;
import com.quxue.template.service.SignDetailService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author harusame
 * @description 针对表【t_sign_detail】的数据库操作Service实现
 * @createDate 2023-10-17 20:12:16
 */
@Service
public class SignDetailServiceImpl extends ServiceImpl<SignDetailMapper, SignDetail>
        implements SignDetailService {

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
    @Resource
    private FaceModelService faceModelService;

    @Resource
    private FaceModelMapper faceModelMapper;

    @Override
    public void validCanSignIn() {
        validCanSign(SignTypeEnum.SIGN_IN);
    }

    @Override
    public void validCanSignOut() {
        validCanSign(SignTypeEnum.SIGN_OUT);
    }

    @Override
    public Date sign(SignDTO signDTO, MultipartFile photo) {
        String userId = tokenUtils.getUserIdFromHeader();
        FaceModel faceModel = faceModelMapper.selectOne(new QueryWrapper<FaceModel>().eq("user_id", userId));
        if (faceModel == null) {
            throw new BusinessException(FaceConst.NO_FACE_MODEL, "当前系统没有该用户的人脸模型");
        }
        faceModelService.verifyFaceModel(photo);//检测上传的图片
        SignDetail signDetail = new SignDetail();
        signDetail.setSignType(signDTO.getSignType());
        signDetail.setAddress(signDTO.getAddress());
        signDetail.setUserId(Integer.valueOf(userId));
        Date date = new Date();
        signDetail.setSignDate(date);
        signDetail.setSignTime(date);
        setSignStatus(userId, signDetail, date);
        signDetailMapper.insert(signDetail);
        return date;
    }

    /**
     * 设置打卡状态
     */
    private void setSignStatus(String userId, SignDetail signDetail, Date date) {
        ClockInRule clockInRule = getClockInRule(userId);
        String now = DateUtil.format(date, "HH:mm");
        if (SignTypeEnum.SIGN_IN == signDetail.getSignType()) {
            String start = clockInRule.getSigninTime();
            String end = clockInRule.getSigninEndTime();
            if (now.compareTo(start) <= 0) {//如果当前时间比正常打卡时间早，则判断正常打卡
                signDetail.setSignStatus(SignStatusEnum.NORMAL);
            } else if (now.compareTo(start) > 0 && now.compareTo(end) < 0) {
                signDetail.setSignStatus(SignStatusEnum.LATE);
            } else {
                throw new BusinessException("已经过了打卡时间");
            }
        } else {
            String early = clockInRule.getSignoutStartTime();
            String start = clockInRule.getSignoutTime();
            String end = clockInRule.getSignoutEndTime();
            if (now.compareTo(start) < 0 && now.compareTo(early) > 0) {//
                signDetail.setSignStatus(SignStatusEnum.EARLY);
            } else if (now.compareTo(start) >= 0 && now.compareTo(end) < 0) {
                signDetail.setSignStatus(SignStatusEnum.NORMAL);
            } else {
                throw new BusinessException("已经过了打卡时间");
            }
        }
    }


    private void validCanSign(SignTypeEnum signType) {
        DateTime date = DateUtil.date();
        isWorkDay(date);
        String userId = tokenUtils.getUserIdFromHeader();
        String formattedDate = DateUtil.format(date, "HH:mm");
        isInTime(userId, formattedDate, signType);
        isSigned(signType, userId);
    }

    /**
     * 判断当天是否已打卡
     *
     * @param signType
     * @param userId
     */
    private void isSigned(SignTypeEnum signType, String userId) {
        if (SignTypeEnum.SIGN_IN == signType) {
            if (signDetailMapper.selectSignInRecord(userId) != null) {
                throw new BusinessException("今天已经打过卡了");
            }
        } else if (SignTypeEnum.SIGN_OUT == signType) {
            if (signDetailMapper.selectSignOutRecord(userId) != null) {
                throw new BusinessException("今天已经打过卡了");
            }
        }
    }

    /**
     * 判断是否为需要上班
     */
    private void isWorkDay(DateTime date) {
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
    protected void isInTime(String userId, String date, SignTypeEnum signType) {
        ClockInRule clockInRule = getClockInRule(userId);
        String start;
        String end;
        if (SignTypeEnum.SIGN_IN == signType) {
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

    private ClockInRule getClockInRule(String userId) {
        String leaderId = userMapper.selectUpdateUserById(userId);
        //查找当前用户的更新用户id，从而找到当前用户的领导设置的打卡时间
        ClockInRule clockInRule;
        if (leaderId == null) {//如果领导id为空，则认为是root用户
            clockInRule = clockInRuleMapper.selectOne(new QueryWrapper<ClockInRule>().eq("user_id", userId));
        } else {
            clockInRule = clockInRuleMapper.selectOne(new QueryWrapper<ClockInRule>().eq("user_id", leaderId));
        }
        return clockInRule;
    }
}




