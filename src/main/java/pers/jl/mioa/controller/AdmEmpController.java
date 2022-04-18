package pers.jl.mioa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.jl.mioa.common.api.CommonResult;
import pers.jl.mioa.mbg.entity.AdmDepartment;
import pers.jl.mioa.mbg.entity.AdmEmployee;
import pers.jl.mioa.service.AdmEmpService;

/**
 * @author: JL Du
 * @date: 2022/4/17 0:01
 * @version: 1.0.0
 */

@RestController
@Api(tags = "后台员工管理")
@RequestMapping("/employee")
public class AdmEmpController {

    @Autowired
    private AdmEmpService admEmpService;

    @ApiOperation("根据员工id获取信息")
    @GetMapping("/getEmployee/{employeeId}")
    public CommonResult getEmployeeById(@PathVariable Long employeeId){
        AdmEmployee emp = admEmpService.getEmpById(employeeId);
        return CommonResult.success(emp);
    }

    @ApiOperation("根据员工id获取部门")
    @GetMapping("/getDept/{employeeId}")
    public CommonResult getDeptById(@PathVariable Long employeeId){
        AdmDepartment dept = admEmpService.getDeptById(employeeId);
        System.out.println("dept内容为："+ dept);
        return CommonResult.success(dept);
    }

}
