package pers.jl.mioa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.jl.mioa.common.api.CommonResult;
import pers.jl.mioa.mbg.entity.AdmLeaveForm;
import pers.jl.mioa.mbg.entity.SysUser;
import pers.jl.mioa.service.LeaveFormService;
import pers.jl.mioa.service.SysUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: JL Du
 * @date: 2022/4/18 15:43
 * @version: 1.0.0
 */
@RestController
@Api(tags = "员工请假管理")
@RequestMapping("/leave")
public class LeaveFormController {

    @Autowired
    private LeaveFormService leaveFormService;

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("员工请假申请")
    @PostMapping("/creatForm")
    public CommonResult createLeaveForm(@RequestBody AdmLeaveForm form){

        //获得form中员工id
        SysUser user = sysUserService.getUserByToken();
        form.setEmployeeId(user.getEmployeeId());
        AdmLeaveForm formParam = leaveFormService.createLeaveForm(form);

        return CommonResult.success(formParam);
    }

    @ApiOperation("获取请假单")
    @PostMapping("getForm")
    public CommonResult getLeaveFormList(){
        SysUser user = sysUserService.getUserByToken();
        List<Map> formList = leaveFormService.getLeaveFormList(user.getEmployeeId());
        Map<String, Object> result = new HashMap<>();
        result.put("count",formList.size());
        result.put("data",formList);
        return CommonResult.success(result);
    }
}
