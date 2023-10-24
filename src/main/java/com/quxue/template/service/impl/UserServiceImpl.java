package com.quxue.template.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.common.constant.UserConst;
import com.quxue.template.common.utils.JWTUtils;
import com.quxue.template.common.utils.TokenUtils;
import com.quxue.template.domain.dto.CreateUserDTO;
import com.quxue.template.domain.dto.UserActiveDTO;
import com.quxue.template.domain.pojo.User;
import com.quxue.template.domain.vo.UserVo;
import com.quxue.template.exception.BusinessException;
import com.quxue.template.exception.SystemException;
import com.quxue.template.service.EmailService;
import com.quxue.template.service.UserService;
import com.quxue.template.mapper.UserMapper;
import com.quxue.template.common.utils.WeChatUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author harusame
 * @description 针对表【t_user】的数据库操作Service实现
 * @createDate 2023-10-17 20:12:16
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService, UserConst {

    @Resource
    private UserMapper userMapper;
    @Resource
    private EmailService emailService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private WeChatUtils weChatUtils;
    @Resource
    private JWTUtils jwtUtils;

    @Resource
    private TokenUtils tokenUtils;

    @Transactional
    @Override
    public String initAdmin(CreateUserDTO admin) {
        return createUser(admin, true);
    }

    @Transactional
    @Override
    public String createCommonUser(CreateUserDTO user) {
        return createUser(user, false);
    }


    private String createUser(CreateUserDTO userDTO, Boolean isRoot) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        user.setHiredate(date);
        if (isRoot) {
            user.setRoot(IS_ROOT);
        } else {
            Integer adminId = Integer.valueOf(tokenUtils.getUserIdFromHeader());
//            Integer adminId = AuthContextHolder.getUserId();  //通过ThreadLocal获取id
            user.setCreateUser(adminId);
            user.setUpdateUser(adminId);
        }
        String code = null;
        if (userMapper.insert(user) == 1) {
            code = generateRandomCode(user);
            String finalCode = code;

            CompletableFuture.runAsync(() -> {
                String subject = "smart-oa初始化激活码";
                String target = user.getEmail();
                String message = String.format("激活码为：%s", finalCode);
                emailService.send(subject, message, target);
            });

/*            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    String subject = "smart-oa初始化激活码";
                    String target = user.getEmail();
                    String message = String.format("激活码为：%s", finalCode);
                    emailService.send(subject, message, target);
                }
            });*/
        }
        if (code != null) {
            return code;
        }
        throw new SystemException("初始化用户失败,原因：插入用户失败");
    }


    @Override
    public String register(UserActiveDTO userActiveDTO) {
        String registerCode = userActiveDTO.getRegisterCode();
        String key = String.format("%s%s", ACTIVE_USER, registerCode);
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String id = ops.get(key);
        if (id == null) {
            throw new BusinessException("激活码错误或已过期");
        }
        String openId = weChatUtils.getOpenId(userActiveDTO.getWeixinCode());

        if (userMapper.selectOne(new QueryWrapper<User>().eq("open_id", openId)) != null) {
            throw new BusinessException("该微信已经存在绑定用户");
        }

        User user = new User();
        user.setId(Integer.valueOf(id));
        user.setOpenId(openId);
        if (userMapper.activateUser(user) == 1) {
            //用户激活后删除激活码
            stringRedisTemplate.delete(key);
            //注册成功后返回令牌
            return jwtUtils.generateToken(id);
        }
        throw new BusinessException("用户激活失败");
    }

    @Override
    public String login(String jsCode) {
        String openId = weChatUtils.getOpenId(jsCode);
        String id = userMapper.selectUserId(openId);
        if (id == null) {
            throw new BusinessException("该微信未绑定用户");
        }
        //用户登录成功返回令牌
        return jwtUtils.generateToken(id);
    }

    @Override
    public Boolean rootVerify(String id) {
        Integer isRoot = userMapper.selectJobById(id);
        System.out.println("isRoot = " + isRoot);
        return Objects.equals(isRoot, IS_ROOT);
    }

    @Override
    public String checkRegisterCode(String registerCode) {
        String id = stringRedisTemplate.opsForValue().get(String.format("%s%s", ACTIVE_USER, registerCode));
        if (id == null) {
            throw new BusinessException("激活码无效或过期");
        }
        String username = userMapper.selectUserNameById(id);
        if (username != null) {
            return username;
        }
        throw new BusinessException("未查询到对应用户");
    }

    @Override
    public UserVo getUserInfo() {
        String id = tokenUtils.getUserIdFromHeader();
        if (StringUtils.isBlank(id)) {
            throw new BusinessException("无效的token");
        }
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", id));
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }


    private String generateRandomCode(User user) {
        String code = RandomUtil.randomNumbers(RANDOM_CODE_LENGTH);
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String keyInRedis = String.format("%s%s", ACTIVE_USER, code);
        Boolean set = ops.setIfAbsent(keyInRedis, String.valueOf(user.getId()), CODE_DURATION);
        if (Boolean.TRUE.equals(set)) {
            return code;
        }
        throw new SystemException("生成验证码出错");
    }
}




