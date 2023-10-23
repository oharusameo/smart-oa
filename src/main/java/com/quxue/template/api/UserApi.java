package com.quxue.template.api;

import com.quxue.template.common.annotation.RequireLogin;
import com.quxue.template.common.annotation.RequireRoot;
import com.quxue.template.common.utils.JWTUtils;
import com.quxue.template.domain.dto.CreateUserDTO;
import com.quxue.template.domain.dto.LoginDTO;
import com.quxue.template.domain.dto.UserActiveDTO;
import com.quxue.template.domain.pojo.Result;
import com.quxue.template.domain.vo.UserVo;
import com.quxue.template.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserApi {

    @Resource
    private UserService userService;


    @PostMapping("/login")
    @ApiOperation("登录员工账号，返回令牌")
    public Result login(@RequestBody @Valid LoginDTO loginDTO) {
        String token = userService.login(loginDTO.getWeixinCode());
        log.info(token);
        return Result.success(token);
    }


    @PostMapping("/register")
    @ApiOperation("激活账号并绑定微信号")
    public Result register(@Valid @RequestBody UserActiveDTO userActiveDTO) {
        String token = userService.register(userActiveDTO);
        return Result.success(token);
    }


    @PostMapping("/createRootUser")
    @ApiOperation("初始化超级管理员")
    public Result createRootUser(@RequestBody @Valid CreateUserDTO userDTO) {
        log.info(userDTO.toString());
        String code = userService.initAdmin(userDTO);
        return Result.success("初始化超级管理员成功", code);
    }

    @PostMapping("/createCommonUser")
    @ApiOperation("创建普通员工")
    @RequireRoot
    public Result createCommonUser(@ApiParam(name = "token", value = "身份认证令牌")
                                   @RequestHeader String token, @RequestBody @Valid CreateUserDTO admin) {
        log.info(token);
        String code = userService.createCommonUser(admin);
        return Result.success("初始化普通员工成功", code);
    }

    @PostMapping("/checkRegisterCode")
    @ApiOperation("根据激活码返回姓名")
    public Result checkRegisterCode(@ApiParam(name = "registerCode", value = "激活码") @Valid
                                    @NotBlank String registerCode) {
        Object username = userService.checkRegisterCode(registerCode);
        return Result.success(username);
    }

    @GetMapping("/getUserInfo")
    @ApiOperation("获取当前员工信息")
    @RequireLogin
    public Result getUserInfo(@ApiParam(name = "token", value = "身份认证令牌")
                              @RequestHeader("token") String token) {
        UserVo userVo = userService.getUserInfo();
        return Result.success(userVo);
    }


}
