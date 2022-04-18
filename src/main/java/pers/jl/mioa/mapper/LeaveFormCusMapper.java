package pers.jl.mioa.mapper;

import pers.jl.mioa.mbg.entity.AdmLeaveForm;

/**
 * @author: JL Du
 * @date: 2022/4/17 16:19
 * @version: 1.0.0
 */
public interface LeaveFormCusMapper {

    /**
     * 创建请假申请表
     *
     * @param form null
     */
    void createLeaveForm(AdmLeaveForm form);

}
