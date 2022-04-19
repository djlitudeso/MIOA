package pers.jl.mioa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.jl.mioa.component.constant.BusinessConstants;
import pers.jl.mioa.mapper.AdmEmpDeptMapper;
import pers.jl.mioa.mapper.LeaveFormCusMapper;
import pers.jl.mioa.mapper.ProcessFlowCusMapper;
import pers.jl.mioa.mbg.entity.AdmEmployee;
import pers.jl.mioa.mbg.entity.AdmLeaveForm;
import pers.jl.mioa.mbg.entity.AdmProcessFlow;
import pers.jl.mioa.mbg.mapper.AdmEmployeeMapper;
import pers.jl.mioa.service.LeaveFormService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 请假单流程服务
 * @author: JL Du
 * @date: 2022/4/17 19:45
 * @version: 1.0.0
 */
@Service
public class LeaveFormServiceImpl implements LeaveFormService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaveFormServiceImpl.class);

    @Autowired
    private LeaveFormCusMapper leaveFormCusMapper;
    @Autowired
    private AdmEmployeeMapper admEmployeeMapper;
    @Autowired
    private ProcessFlowCusMapper processFlowCusMapper;
    @Autowired
    private AdmEmpDeptMapper admEmpDeptMapper;

    /**
     * 创建请假单
     *
     * @param form 数据
     * @return 请假单
     */
    @Override
    public AdmLeaveForm createLeaveForm(AdmLeaveForm form) {
        AdmEmployee employee = admEmployeeMapper.selectByPrimaryKey(form.getEmployeeId());
        form.setCreateTime(new Date());
        if(employee.getLevel() == BusinessConstants.GENERAL_MANAGER_LEVEL){
            form.setState("approved");
        }else{
            form.setState("processing");
        }
        leaveFormCusMapper.createLeaveForm(form);

        AdmProcessFlow processFlow1 = new AdmProcessFlow();
        processFlow1.setFormId(form.getFormId());
        processFlow1.setOperatorId(employee.getEmployeeId());
        processFlow1.setAction("apply");
        processFlow1.setCreateTime(new Date());
        processFlow1.setOrderNo(1);
        processFlow1.setState("complete");
        processFlow1.setIsLast(0);
        processFlowCusMapper.createProcessFlow(processFlow1);
        // 7级以下员工，生成部门经理审批任务，请假时间大于72小时，还需要生成总经理审批任务
        if(employee.getLevel() < BusinessConstants.DEPARTMENT_MANAGER_LEVEL){
            AdmEmployee dmanager = admEmpDeptMapper.getLeader(employee);
            AdmProcessFlow processFlow2 = new AdmProcessFlow();
            processFlow2.setFormId(form.getFormId());
            processFlow2.setOperatorId(dmanager.getEmployeeId());
            processFlow2.setAction("audit");
            processFlow2.setCreateTime(new Date());
            processFlow2.setOrderNo(2);
            processFlow2.setState("process");
            long diff = form.getEndTime().getTime() - form.getStartTime().getTime();
            float hours = (float) (diff/(1000*60*60) * 1.0);
            if(hours >= BusinessConstants.MANAGER_AUDIT_HOURS){
                processFlow2.setIsLast(0);
                processFlowCusMapper.createProcessFlow(processFlow2);
                AdmEmployee manager = admEmpDeptMapper.getLeader(dmanager);
                AdmProcessFlow processFlow3 = new AdmProcessFlow();
                processFlow3.setFormId(form.getFormId());
                processFlow3.setOperatorId(manager.getEmployeeId());
                processFlow3.setAction("audit");
                processFlow3.setCreateTime(new Date());
                processFlow3.setOrderNo(3);
                processFlow3.setState("ready");
                processFlow3.setIsLast(1);
                processFlowCusMapper.createProcessFlow(processFlow3);
            }else{
                processFlow2.setIsLast(1);
                processFlowCusMapper.createProcessFlow(processFlow2);
            }
            //部门经理
        } else if (employee.getLevel() == BusinessConstants.DEPARTMENT_MANAGER_LEVEL) {
            // 7级员工,生成总经理审批任务
            AdmEmployee manager = admEmpDeptMapper.getLeader(employee);
            AdmProcessFlow processFlow4 = new AdmProcessFlow();
            processFlow4.setFormId(form.getFormId());
            processFlow4.setOperatorId(manager.getEmployeeId());
            processFlow4.setAction("audit");
            processFlow4.setCreateTime(new Date());
            processFlow4.setOrderNo(2);
            processFlow4.setState("process");
            processFlow4.setIsLast(1);
            processFlowCusMapper.createProcessFlow(processFlow4);
        } else if (employee.getLevel() == BusinessConstants.GENERAL_MANAGER_LEVEL) {
            // 8级员工,生成总经理审批任务,系统自动通过
            AdmProcessFlow processFlow5 = new AdmProcessFlow();
            processFlow5.setFormId(form.getFormId());
            processFlow5.setOperatorId(employee.getEmployeeId());
            processFlow5.setAction("audit");
            processFlow5.setResult("approved");
            processFlow5.setReason("自动通过");
            processFlow5.setCreateTime(new Date());
            processFlow5.setAuditTime(new Date());
            processFlow5.setState("complete");
            processFlow5.setOrderNo(2);
            processFlow5.setIsLast(1);
            processFlowCusMapper.createProcessFlow(processFlow5);
        }
        return form;
    }

    /**
     * 获取请假单
     *
     * @param operatorId 经办人id
     * @return 请假单
     */
    @Override
    public List<Map> getLeaveFormList(Long operatorId) {

        List<Map> formList = leaveFormCusMapper.selectByParams(operatorId);

        return formList;
    }


}
