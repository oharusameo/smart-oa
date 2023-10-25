package com.quxue.template.api;

import com.quxue.template.common.annotation.RequireLogin;
import com.quxue.template.domain.pojo.Result;
import com.quxue.template.service.FaceModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/faceAuth")
@Api(tags = "人脸模型管理", value = "FaceAuthApi")
@RequireLogin
public class FaceAuthApi {
    @Resource
    private FaceModelService faceModelService;


    @PostMapping("/createFaceModel")
    @ApiOperation("创建人脸模型")
    public Result createFaceModel(@ApiParam(name = "token", value = "身份认证令牌")
                                  @RequestHeader String token, @ApiParam(name = "photo", value = "考勤打卡的照片")
                                  @RequestPart("photo") MultipartFile photo) {
        faceModelService.createFaceModel(photo);
        return Result.successMsg("人脸模型创建成功");
    }


}
