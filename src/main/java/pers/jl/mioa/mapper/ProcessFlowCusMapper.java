package pers.jl.mioa.mapper;

import pers.jl.mioa.mbg.entity.AdmProcessFlow;

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

}
