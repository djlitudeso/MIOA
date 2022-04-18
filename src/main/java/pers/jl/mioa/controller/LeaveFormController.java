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
import pers.jl.mioa.service.LeaveFormService;

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

    @ApiOperation("请假申请功能")
    @PostMapping("/form")
    public CommonResult createLeaveForm(@RequestBody AdmLeaveForm form){
        AdmLeaveForm formParam = leaveFormService.createLeaveForm(form);

        return CommonResult.success(formParam);
    }

}
