package pers.jl.mioa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.jl.mioa.common.api.CommonResult;
import pers.jl.mioa.dto.SysUserLoginParam;
import pers.jl.mioa.dto.SysUserRegisterParam;
import pers.jl.mioa.dto.UpdateUserPasswordParam;
import pers.jl.mioa.mbg.entity.SysMenu;
import pers.jl.mioa.mbg.entity.SysUser;
import pers.jl.mioa.service.SysUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: JL Du
 * @date: 2022/4/11 0:40
 * @version: 1.0.0
 */

@RestController
@Api(tags = "后台用户管理")
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult<SysUser> register(@Validated @RequestBody SysUserRegisterParam sysUserRegisterParam) {
        SysUser sysUser = sysUserService.register(sysUserRegisterParam);
        if (sysUser == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(sysUser);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(@Validated @RequestBody SysUserLoginParam sysUserLoginParam) {
        String token = sysUserService.login(sysUserLoginParam.getUsername(),
                                            sysUserLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "退出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @ApiOperation("获取用户所有权限（包括+-权限）")
    @RequestMapping(value = "/menu/{userId}", method = RequestMethod.GET)
    public CommonResult<List<SysMenu>> getMenuList(@PathVariable Long userId) {
        List<SysMenu> sysMenuList = sysUserService.getMenuList(userId);
        return CommonResult.success(sysMenuList);
    }

    @ApiOperation("修改用户密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public CommonResult updatePassword(@Validated @RequestBody UpdateUserPasswordParam updatePasswordParam) {
        int status = sysUserService.updatePassword(updatePasswordParam);
        if (status > 0) {
            return CommonResult.success(status);
        } else if (status == -1) {
            return CommonResult.failed("提交参数不合法");
        } else if (status == -2) {
            return CommonResult.failed("找不到该用户");
        } else if (status == -3) {
            return CommonResult.failed("旧密码错误");
        } else {
            return CommonResult.failed();
        }
    }
    @ApiOperation("根据id获取用户")
    @GetMapping("/getUser/{userId}")
    public CommonResult getUserById(@PathVariable Long userId){
        SysUser user = sysUserService.getUserById(userId);
        return CommonResult.success(user);
    }

}
