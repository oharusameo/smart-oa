package com.quxue.template.api;

import com.quxue.template.domain.dto.AdminInitDTO;
import com.quxue.template.domain.pojo.Result;
import com.quxue.template.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/vi/user")
@Slf4j
public class AdminInitApi {

    @Resource
    private UserService userService;

    @PostMapping("/createRootUser")
    @ApiOperation("初始化超级管理员")
    public Result createRootUser(@RequestBody @Valid AdminInitDTO admin) {
        System.out.println("admin = " + admin);
        String code = userService.initAdmin(admin);
        if (code != null) {
            return Result.success("初始化超级管理员成功", code);
        }
        return Result.error("初始化超级管理员失败");
    }
}
