package com.quxue.template.service;

import com.quxue.template.common.enums.SignTypeEnum;
import com.quxue.template.domain.dto.SignDTO;
import com.quxue.template.domain.pojo.SignDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author harusame
 * @description 针对表【t_sign_detail】的数据库操作Service
 * @createDate 2023-10-17 20:12:16
 */
public interface SignDetailService extends IService<SignDetail> {

    void validCanSignIn();

    void validCanSignOut();


    Date sign(SignDTO signDTO, MultipartFile file);

}
