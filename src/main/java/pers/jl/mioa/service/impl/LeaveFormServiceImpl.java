package pers.jl.mioa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.jl.mioa.common.exception.Asserts;
import pers.jl.mioa.component.constant.BusinessConstants;
import pers.jl.mioa.mapper.AdmEmpDeptMapper;
import pers.jl.mioa.mapper.LeaveFormCusMapper;
import pers.jl.mioa.mapper.NoticeCusMapper;
import pers.jl.mioa.mapper.ProcessFlowCusMapper;
import pers.jl.mioa.mbg.entity.AdmEmployee;
import pers.jl.mioa.mbg.entity.AdmLeaveForm;
import pers.jl.mioa.mbg.entity.AdmProcessFlow;
import pers.jl.mioa.mbg.entity.SysNotice;
import pers.jl.mioa.mbg.mapper.AdmEmployeeMapper;
import pers.jl.mioa.service.LeaveFormService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private NoticeCusMapper noticeCusMapper;

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
        // noticeContent 中所需要的时间格式定义
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");

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

            // noticeContent：SysNotice表中 content 字段内容定义
            String noticeContent1 = String.format("您的请假申请[%s-%s]已提交,，请等待上级审批.",
                    sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            // 请假单已提交消息
            noticeCusMapper.createNotice(new SysNotice(employee.getEmployeeId(),noticeContent1));

            // 通知部门经理审批消息
            String noticeContent2 = String.format("%s-%s提起请假申请[%s-%s],请尽快审批.", employee.getTitle(),
                    employee.getName(), sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            noticeCusMapper.createNotice(new SysNotice(dmanager.getEmployeeId(),noticeContent2));

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

            // noticeContent：SysNotice表中 content 字段内容定义
            String noticeContent1 = String.format("您的请假申请[%s-%s]已提交,，请等待上级审批.",
                    sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            // 请假单已提交消息
            noticeCusMapper.createNotice(new SysNotice(employee.getEmployeeId(),noticeContent1));

            // noticeContent：SysNotice表中 content 字段内容定义
            String noticeContent2 = String.format("%s-%s提起请假申请[%s-%s],请尽快审批.", employee.getTitle(),
                    employee.getName(), sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            // 通知总经理审批消息
            noticeCusMapper.createNotice(new SysNotice(manager.getEmployeeId(),noticeContent2));

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

            // noticeContent：SysNotice表中 content 字段内容定义
            String noticeContent = String.format("您的请假申请[%s-%s]系统已自动批准通过.",
                    sdf.format(form.getStartTime()), sdf.format(form.getEndTime()));
            // 总经理 请假单已提交消息
            noticeCusMapper.createNotice(new SysNotice(employee.getEmployeeId(),noticeContent));

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

        return leaveFormCusMapper.selectByParams(operatorId);

    }

    /**
     * 请假单审批功能
     *
     * @param formId     请假单编号
     * @param operatorId 经办人id
     * @param result     审批结果
     * @param reason     审批意见
     */
    @Override
    public void auditLeaveForm(Long formId, Long operatorId, String result, String reason) {
        // 1.无论'同意'or'驳回'，当前任务状态变更为 complete
        List<AdmProcessFlow> flowList = processFlowCusMapper.selectProcessFlowByFormId(formId);
        if (flowList.size() == 0) {
            Asserts.fail("无效的审批流程");
        }
        //获取 符合条件的 当前任务 ProcessFlow对象
        List<AdmProcessFlow> processList =
                flowList.stream()
                        .filter(p -> p.getOperatorId().equals(operatorId) && "process".equals(p.getState()))
                        .collect(Collectors.toList());
        AdmProcessFlow processFlow = null;
        if (processList.size() == 0) {
            Asserts.fail("未找到待处理任务");
        } else {
            processFlow = processList.get(0);
            processFlow.setState("complete");
            processFlow.setResult(result);
            processFlow.setReason(reason);
            processFlow.setAuditTime(new Date());
            processFlowCusMapper.updateProcessFlow(processFlow);
        }

        AdmLeaveForm form = leaveFormCusMapper.selectLeaveFormById(formId);

        // noticeContent 中所需要的时间格式定义
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH时");
        // 表单提交人信息
        AdmEmployee employee = admEmployeeMapper.selectByPrimaryKey(form.getEmployeeId());
        // 任务经办人信息
        AdmEmployee operator = admEmployeeMapper.selectByPrimaryKey(operatorId);

        // 2.如果当前任务是最后一个节点，代表流程结束，更新请假单状态为 'approved' or 'refused'
        if (processFlow.getIsLast() == 1) {
            // result : approved || refused
            form.setState(result);
            leaveFormCusMapper.updateLeaveForm(form);

            String var = null;
            if ("approved".equals(result)) {
                var = "批准";
            } else if ("refused".equals(result)){
                var = "驳回";
            }

            // noticeContent：SysNotice表中 content 字段内容定义
            //发送给表单提交人的通知
            String noticeContent1 = String.format("您的请假申请[%s-%s]%s%s已%s,审批意见:%s,审批流程已结束.",
                    sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                    operator.getTitle(),operator.getName(), var, reason);
            noticeCusMapper.createNotice(new SysNotice(form.getEmployeeId(),noticeContent1));
            // 发给审批人的通知
            String noticeContent2 = String.format("%s-%s提起请假申请[%s-%s]您已%s,审批意见:%s,审批流程已结束.",
                    employee.getTitle(), employee.getName(),
                    sdf.format(form.getStartTime()), sdf.format(form.getEndTime()), var, reason);
            noticeCusMapper.createNotice(new SysNotice(operator.getEmployeeId(),noticeContent2));

        }
        else {
            // readyList 包含所有后续任务节点
            List<AdmProcessFlow> readyList =
                    flowList.stream()
                            .filter(p -> "ready".equals(p.getState()))
                            .collect(Collectors.toList());
            // 3.如果当前任务不是最后一个节点且审批同意，那下一个节点的状态应由 'ready' 改为 'process'
            if (BusinessConstants.AUDIT_RESULT_APPROVED.equals(result)) {
                AdmProcessFlow readyProcess = readyList.get(0);
                readyProcess.setState("process");
                processFlowCusMapper.updateProcessFlow(readyProcess);

                // 消息1：发送给表单提交人，部分经理已经审核通过，交由上级继续审核
                String noticeContent1 = String.format("您的请假申请[%s-%s]%s%s已批准,审批意见:%s,交由上级继续审核.",
                        sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                        operator.getTitle(),operator.getName(), reason);
                noticeCusMapper.createNotice(new SysNotice(form.getEmployeeId(),noticeContent1));

                // 消息2：通知总经理有新的审批任务
                String noticeContent2 = String.format("%s-%s提起请假申请[%s-%s],请尽快审批.",
                        employee.getTitle(), employee.getName(),
                        sdf.format(form.getStartTime()), sdf.format(form.getEndTime()) );
                noticeCusMapper.createNotice(new SysNotice(readyProcess.getOperatorId(), noticeContent2));

                // 消息3：通知部门经理（当前经办人），员工的申请您已批准，交由上级继续审核
                String noticeContent3 = String.format("%s-%s提起请假申请[%s-%s]您已批准,审批意见:%s,交由上级继续审核.",
                        employee.getTitle(), employee.getName(),
                        sdf.format(form.getStartTime()), sdf.format(form.getEndTime()) ,reason);
                noticeCusMapper.createNotice(new SysNotice(operator.getEmployeeId(),noticeContent3));

            }
            else if (BusinessConstants.AUDIT_RESULT_REFUSED.equals(result)) {
                // 4.如果当前任务不是最后一个节点且审批驳回，则后续所有任务状态变更为 'cancel' ,请假单状态变为 'refused'
                for ( AdmProcessFlow p : readyList ) {
                    p.setState("cancel");
                    processFlowCusMapper.updateProcessFlow(p);
                }
                form.setState("refused");
                leaveFormCusMapper.updateLeaveForm(form);

                // 消息1：发送给表单提交人，申请已被驳回
                String noticeContent1 = String.format("您的请假申请[%s-%s]%s%s已驳回,审批意见:%s,交由上级继续审核.",
                        sdf.format(form.getStartTime()), sdf.format(form.getEndTime()),
                        operator.getTitle(),operator.getName(), reason);
                noticeCusMapper.createNotice(new SysNotice(form.getEmployeeId(),noticeContent1));

                // 消息2：通知经办人您已驳回 请假申请
                String noticeContent2 = String.format("%s-%s提起请假申请[%s-%s],您已驳回.",
                        employee.getTitle(), employee.getName(),
                        sdf.format(form.getStartTime()), sdf.format(form.getEndTime()) );
                noticeCusMapper.createNotice(new SysNotice(operator.getEmployeeId(), noticeContent2));

            }
        }

    }

}
