package pers.jl.mioa.mapper;

import org.apache.ibatis.annotations.Param;
import pers.jl.mioa.mbg.entity.AdmLeaveForm;

import java.util.List;
import java.util.Map;

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

    /**
     * null
     *
     * @param operatorId null
     * @return null
     */
    List<Map> selectByParams(@Param("pf_operator_id") Long operatorId);

}
