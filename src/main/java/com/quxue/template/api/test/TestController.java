package com.quxue.template.api.test;

import com.quxue.template.domain.dto.ValidationRequestParam;
import com.quxue.template.domain.pojo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @PostMapping("/validate.do")
    @ApiOperation("手机号码格式校验测试")
    public Result testApi(@Valid @RequestBody ValidationRequestParam requestParam) {

            return Result.success();
    }
}
