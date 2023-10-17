package com.quxue.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.domain.pojo.FaceModel;
import com.quxue.template.service.FaceModelService;
import com.quxue.template.mapper.FaceModelMapper;
import org.springframework.stereotype.Service;

/**
* @author ggzst
* @description 针对表【t_face_model】的数据库操作Service实现
* @createDate 2023-10-17 19:02:12
*/
@Service
public class FaceModelServiceImpl extends ServiceImpl<FaceModelMapper, FaceModel>
    implements FaceModelService{

}



