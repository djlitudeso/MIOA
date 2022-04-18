package pers.jl.mioa.service;

import pers.jl.mioa.mbg.entity.AdmDepartment;
import pers.jl.mioa.mbg.entity.AdmEmployee;

/**
 * @author: JL Du
 * @date: 2022/4/16 23:59
 * @version: 1.0.0
 */

public interface AdmEmpService {

    /**
     * 根据员工id获取员工信息
     *
     * @param employeeId null
     * @return null
     */
    AdmEmployee getEmpById(Long employeeId);

    /**
     * 根据员工id获取所在部门
     *
     * @param employeeId null
     * @return null
     */
    AdmDepartment getDeptById(Long employeeId);

}
