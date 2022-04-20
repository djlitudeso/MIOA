package pers.jl.mioa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.jl.mioa.common.api.CommonResult;
import pers.jl.mioa.mbg.entity.SysNotice;
import pers.jl.mioa.mbg.entity.SysUser;
import pers.jl.mioa.service.SysNoticeService;
import pers.jl.mioa.service.SysUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: JL Du
 * @date: 2022/4/20 23:25
 * @version: 1.0.0
 */
@RestController
@Api(tags = "系统消息管理")
@RequestMapping("/notice")
public class SysNoticeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysNoticeController.class);

    @Autowired
    private SysNoticeService sysNoticeService;
    @Autowired
    private SysUserService sysUserService;


    @ApiOperation("员工系统消息列表")
    @GetMapping("/getNoticeList")
    public CommonResult getNoticeList() {
        SysUser user = sysUserService.getUserByToken();
        List<SysNotice> noticeList =  sysNoticeService.getNoticeList(user.getEmployeeId());
        Map<String, Object> result = new HashMap<>();
        result.put("count",noticeList.size());
        result.put("data",noticeList);

        return CommonResult.success(result);
    }


}
