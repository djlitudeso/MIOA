package pers.jl.mioa.service;

import pers.jl.mioa.mbg.entity.AdmLeaveForm;

import java.util.List;
import java.util.Map;

/**
 * 请假单流程
 *
 * @author: JL Du
 * @date: 2022/4/17 19:44
 * @version: 1.0.0
 */

public interface LeaveFormService {

    /**
     *创建请假单
     *
     * @param form 数据
     * @return 请假单
     */
    AdmLeaveForm createLeaveForm(AdmLeaveForm form);

    /**
     * 获取请假单
     *
     * @param operatorId 经办人id
     * @return 请假单
     */
    List<Map> getLeaveFormList(Long operatorId);

    /**
     * 请假单审批功能
     *
     * @param formId 请假单编号
     * @param operatorId 经办人id
     * @param result 审批结果
     * @param reason 审批意见
     */
    void auditLeaveForm(Long formId,Long operatorId,String result,String reason);
}
