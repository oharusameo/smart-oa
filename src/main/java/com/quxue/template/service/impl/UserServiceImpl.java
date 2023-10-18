package com.quxue.template.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.constant.UserConst;
import com.quxue.template.domain.dto.AdminInitDTO;
import com.quxue.template.domain.pojo.User;
import com.quxue.template.service.UserService;
import com.quxue.template.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    @Transactional
    public String initAdmin(AdminInitDTO admin) {
        User user = new User();
        BeanUtils.copyProperties(admin, user);
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        user.setHiredate(date);
        user.setRoot(UserConst.IS_ROOT);
        String code = null;
        if (userMapper.insert(user) == 1) {
            code = generateRandomCode(user);
        }
        return code;
    }


    private String generateRandomCode(User user) {
        String code = RandomUtil.randomNumbers(8);
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        Boolean set = ops.setIfAbsent(code, String.valueOf(user.getId()), Duration.ofMinutes(10));
        if (Boolean.TRUE.equals(set)) {
            return code;
        }
        return null;
    }
}




