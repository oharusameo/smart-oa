package com.quxue.template.api;

import com.quxue.template.domain.dto.AdminInitDTO;
import com.quxue.template.domain.dto.UserActiveDTO;
import com.quxue.template.domain.pojo.Result;
import com.quxue.template.service.UserService;
import com.quxue.template.utils.WeChatUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/vi/user")
@Slf4j
public class UserApi {

    @Resource
    private UserService userService;


    @PostMapping("/register")
    @ApiOperation("激活账号绑定微信号")
    public Result register(@Valid @RequestBody UserActiveDTO userActiveDTO) {
        String token = userService.register(userActiveDTO);
        return Result.success(token);
    }


    @PostMapping("/createRootUser")
    @ApiOperation("初始化超级管理员")
    public Result createRootUser(@RequestBody @Valid AdminInitDTO admin) {
        log.info(admin.toString());
        String code = userService.initAdmin(admin);
        return Result.success("初始化超级管理员成功", code);
    }
}
