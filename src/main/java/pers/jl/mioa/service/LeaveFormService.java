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
    public AdmLeaveForm createLeaveForm(AdmLeaveForm form);

    /**
     * 获取请假单
     *
     * @param operatorId 经办人id
     * @return 请假单
     */
    List<Map> getLeaveFormList(Long operatorId);
}
