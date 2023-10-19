package com.quxue.template.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.constant.UserConst;
import com.quxue.template.domain.dto.AdminInitDTO;
import com.quxue.template.domain.dto.UserActiveDTO;
import com.quxue.template.domain.pojo.User;
import com.quxue.template.exception.BusinessException;
import com.quxue.template.service.EmailService;
import com.quxue.template.service.UserService;
import com.quxue.template.mapper.UserMapper;
import com.quxue.template.utils.WeChatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * @author harusame
 * @description 针对表【t_user】的数据库操作Service实现
 * @createDate 2023-10-17 20:12:16
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService ,UserConst{

    @Resource
    private UserMapper userMapper;
    @Resource
    private EmailService emailService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private WeChatUtils weChatUtils;

    @Override
    @Transactional
    public String initAdmin(AdminInitDTO admin) {
        User user = new User();
        BeanUtils.copyProperties(admin, user);
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        user.setHiredate(date);
        user.setRoot(IS_ROOT);
        String code = null;
        if (userMapper.insert(user) == 1) {
            code = generateRandomCode(user);
            String finalCode = code;
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    String subject = "smart-oa管理员初始化激活码";
                    String target = user.getEmail();
                    String message = "激活码为：" + finalCode;
                    emailService.send(subject, message, target);
        }
    });
        }
        return code;
    }

    @Override
    public String register(UserActiveDTO userActiveDTO) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String id = ops.get(ACTIVE_USER+userActiveDTO.getRegisterCode());
        if (id == null) {
            throw new BusinessException("激活码错误，没有找到相应的激活码");
        }
        String openId = weChatUtils.getOpenId(userActiveDTO.getTempCAPTCHA());
        User user = new User();
        user.setId(Integer.valueOf(id));
        user.setOpenId(openId);
        if (userMapper.activateUser(user) == 1) {
            String token = "注册成功，返回由JWT工具类生成的token";
            return token;
        }
        throw new BusinessException("用户激活失败");
    }


    private String generateRandomCode(User user) {
        String code = RandomUtil.randomNumbers(8);
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String key=ACTIVE_USER+code;
        Boolean set = ops.setIfAbsent(key, String.valueOf(user.getId()), Duration.ofMinutes(10));
        if (Boolean.TRUE.equals(set)) {
            return code;
        }
        return null;
    }
}




