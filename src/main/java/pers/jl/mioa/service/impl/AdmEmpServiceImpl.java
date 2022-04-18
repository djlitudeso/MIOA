package pers.jl.mioa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.jl.mioa.mapper.AdmEmpDeptMapper;
import pers.jl.mioa.mbg.entity.AdmDepartment;
import pers.jl.mioa.mbg.entity.AdmEmployee;
import pers.jl.mioa.mbg.mapper.AdmEmployeeMapper;
import pers.jl.mioa.service.AdmEmpService;

/**
 * @author: JL Du
 * @date: 2022/4/17 0:00
 * @version: 1.0.0
 */
@Service
public class AdmEmpServiceImpl implements AdmEmpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdmEmpServiceImpl.class);

    @Autowired
    private AdmEmployeeMapper admEmployeeMapper;
    @Autowired
    private AdmEmpDeptMapper admEmpDeptMapper;

    /**
     * 根据id获取员工信息
     *
     * @param employeeId null
     * @return null
     */
    @Override
    public AdmEmployee getEmpById(Long employeeId) {
        return admEmployeeMapper.selectByPrimaryKey(employeeId);
    }

    /**
     * 根据员工id获取所在部门
     *
     * @param employeeId null
     * @return null
     */
    @Override
    public AdmDepartment getDeptById(Long employeeId) {
        return admEmpDeptMapper.getDeptById(employeeId);
    }
}
