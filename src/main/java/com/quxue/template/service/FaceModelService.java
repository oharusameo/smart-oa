package com.quxue.template.service;

import com.quxue.template.domain.pojo.FaceModel;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author harusame
 * @description 针对表【t_face_model】的数据库操作Service
 * @createDate 2023-10-17 20:12:16
 */
public interface FaceModelService extends IService<FaceModel> {

    void createFaceModel(MultipartFile photo);
}
