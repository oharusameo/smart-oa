package com.quxue.template.service;

import com.quxue.template.domain.dto.CreateUserDTO;
import com.quxue.template.domain.dto.UserActiveDTO;
import com.quxue.template.domain.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quxue.template.domain.vo.UserVo;

/**
 * @author harusame
 * @description 针对表【t_user】的数据库操作Service
 * @createDate 2023-10-17 20:12:16
 */
public interface UserService extends IService<User> {

    String initAdmin(CreateUserDTO userDTO);

    String createCommonUser(CreateUserDTO userDTO);

    String register(UserActiveDTO userActiveDTO);

    String login(String jsCode);

    Boolean rootVerify(String id);


    String checkRegisterCode(String registerCode);

    UserVo getUserInfo();
}
