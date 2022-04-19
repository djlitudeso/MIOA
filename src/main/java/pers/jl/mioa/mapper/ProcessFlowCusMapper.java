package pers.jl.mioa.mapper;

import org.apache.ibatis.annotations.Param;
import pers.jl.mioa.mbg.entity.AdmProcessFlow;

import java.util.List;

/**
 * @author: JL Du
 * @date: 2022/4/17 17:47
 * @version: 1.0.0
 */
public interface ProcessFlowCusMapper {

    /**
     * 创建处理流程
     *
     * @param processFlow null
     */
    void createProcessFlow(AdmProcessFlow processFlow);

    /**
     * 通过id获取处理流程表
     *
     * @param formId 请假单编号
     * @return  流程表
     */
    List<AdmProcessFlow> selectProcessFlowByFormId(@Param("formId") Long formId);

    /**
     * 更新处理流程表
     *
     * @param processFlow 流程表
     */
    void updateProcessFlow(AdmProcessFlow processFlow);

}
