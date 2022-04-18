package pers.jl.mioa.mapper;

import org.apache.ibatis.annotations.Param;
import pers.jl.mioa.mbg.entity.AdmDepartment;
import pers.jl.mioa.mbg.entity.AdmEmployee;

/**
 * @author: JL Du
 * @date: 2022/4/17 0:35
 * @version: 1.0.0
 */

public interface AdmEmpDeptMapper {

    /**
     * 根据员工id获取所在部门
     *
     * @param employeeId null
     * @return null
     */
    AdmDepartment getDeptById(@Param("employeeId") Long employeeId);

    /**
     * 获取上级主管对象
     *
     * @param employee 员工
     * @return 上级主管对象
     */
    AdmEmployee getLeader(@Param("employee") AdmEmployee employee);

}
