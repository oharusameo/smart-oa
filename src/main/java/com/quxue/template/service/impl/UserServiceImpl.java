package com.quxue.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.domain.pojo.User;
import com.quxue.template.service.UserService;
import com.quxue.template.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author ggzst
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2023-10-17 19:02:12
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




