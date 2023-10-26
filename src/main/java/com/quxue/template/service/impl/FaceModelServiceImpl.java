package com.quxue.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.common.constant.FaceConst;
import com.quxue.template.common.utils.TokenUtils;
import com.quxue.template.domain.pojo.FaceModel;
import com.quxue.template.domain.pojo.User;
import com.quxue.template.exception.BusinessException;
import com.quxue.template.exception.SystemException;
import com.quxue.template.mapper.UserMapper;
import com.quxue.template.service.FaceModelService;
import com.quxue.template.mapper.FaceModelMapper;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.iai.v20200303.IaiClient;
import com.tencentcloudapi.iai.v20200303.models.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author harusame
 * @description 针对表【t_face_model】的数据库操作Service实现
 * @createDate 2023-10-17 20:12:16
 */
@Service
@Slf4j
public class FaceModelServiceImpl extends ServiceImpl<FaceModelMapper, FaceModel>
        implements FaceModelService, FaceConst {
    @Value("${tencent-cloud.secret-id}")
    private String secretId;
    @Value("${tencent-cloud.secret-key}")
    private String secretKey;
    @Value("${tencent-cloud.group-id}")
    private String groupId;
    @Value("${tencent-cloud.region}")
    private String region;
    @Resource
    private TokenUtils tokenUtils;
    @Resource
    private UserMapper userMapper;
    @Resource
    private FaceModelMapper faceModelMapper;
    List<String> fileType = Arrays.asList("jpg", "png", "jpeg");

    @Override
    public void createFaceModel(MultipartFile photo) {
        String photoStr = verifyAndGetPhoto(photo);
        String userId = tokenUtils.getUserIdFromHeader();
        Credential cred = new Credential(secretId, secretKey);
        IaiClient client = new IaiClient(cred, region);
        CreatePersonRequest req = new CreatePersonRequest();
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", userId).select("username"));
        req.setGroupId(groupId);
        req.setPersonName(user.getUsername());
        req.setPersonId(userId);
        req.setUniquePersonControl(4L);
        req.setQualityControl(4L);
        req.setImage(photoStr);//设置图片
        CreatePersonResponse resp;
        try {
            resp = client.CreatePerson(req);
        } catch (TencentCloudSDKException e) {
            throw new SystemException(e.getMessage());
        }
        if (StringUtils.isNotBlank(resp.getSimilarPersonId())) {
            throw new BusinessException("存在相似的人脸模型，人脸模型创建失败");
        }
        String faceId = resp.getFaceId();
        FaceModel faceModel = new FaceModel();
        faceModel.setFaceModelId(faceId);
        faceModel.setUserId(Integer.valueOf(userId));
        faceModelMapper.insert(faceModel);
    }

    @Override
    public void verifyFaceModel(MultipartFile photo) {
        String photoStr = verifyAndGetPhoto(photo);
        Credential cred = new Credential(secretId, secretKey);
        checkLiveFace(cred, photoStr);
        checkIsMatch(cred, photoStr);
    }


    private void checkIsMatch(Credential cred, String photo) {
        IaiClient client = new IaiClient(cred, region);
        VerifyFaceRequest req = new VerifyFaceRequest();
        req.setImage(photo);
        req.setPersonId(tokenUtils.getUserIdFromHeader());
        req.setQualityControl(4L);
        VerifyFaceResponse resp;
        try {
            resp = client.VerifyFace(req);
        } catch (TencentCloudSDKException e) {
            throw new BusinessException(e.getMessage());
        }
        if (!resp.getIsMatch()) {
            throw new BusinessException(FACE_MODEL_MISMATCH, "人脸不匹配");
        }
    }

    /**
     * 检查上传的图片是否活人
     */
    private void checkLiveFace(Credential cred, String photo) {
        IaiClient client = new IaiClient(cred, region);
        DetectLiveFaceAccurateRequest req = new DetectLiveFaceAccurateRequest();
        req.setImage(photo);
        DetectLiveFaceAccurateResponse resp;
        try {
            resp = client.DetectLiveFaceAccurate(req);
        } catch (TencentCloudSDKException e) {
            throw new BusinessException(e.getMessage());
        }
        if (resp.getScore() < 70) {
            throw new BusinessException(NOT_LIVE_FACE, "不允许上传翻拍照片");
        }
    }


    private String verifyAndGetPhoto(MultipartFile photo) {
        String extension = FileNameUtils.getExtension(photo.getOriginalFilename());
        if (photo.isEmpty() || !fileType.contains(extension)) {
            throw new BusinessException("上传的图片不合法");
        }
        String photoStr;
        try {
            photoStr = Base64Utils.encodeToString(photo.getBytes());
        } catch (IOException e) {
            throw new BusinessException("图片解码失败");
        }
        return photoStr;
    }
}




