package com.quxue.template.api.test;

import com.quxue.template.domain.dto.EmailDTO;
import com.quxue.template.domain.dto.ValidationRequestParam;
import com.quxue.template.domain.dto.XssReqParam;
import com.quxue.template.domain.pojo.Result;
import com.quxue.template.service.EmailService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/validate.do")
    @ApiOperation("手机号码格式校验测试")
    public Result testApi(@Valid @RequestBody ValidationRequestParam requestParam) {

        return Result.success();
    }

    @PostMapping("/xss.do")
    @ApiOperation("xss脚本攻击测试")
    public Result testXss(@Valid @RequestBody XssReqParam requestParam) {
        System.out.println(requestParam.getMessage());
        return Result.success();
    }

    @PostMapping("/email")
    @ApiOperation("发送邮件测试")
    public Result testEmail(@Valid @RequestBody EmailDTO emailDTO) {
//        return emailService.send(emailDTO.getSubject(), emailDTO.getMessage(), emailDTO.getTarget());
        return null;
    }
}
