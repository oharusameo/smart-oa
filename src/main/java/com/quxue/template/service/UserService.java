package com.quxue.template.service;

import com.quxue.template.domain.dto.AdminInitDTO;
import com.quxue.template.domain.dto.UserActiveDTO;
import com.quxue.template.domain.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author harusame
 * @description 针对表【t_user】的数据库操作Service
 * @createDate 2023-10-17 20:12:16
 */
public interface UserService extends IService<User> {

    String initAdmin(AdminInitDTO admin);

    String register(UserActiveDTO userActiveDTO);
}
