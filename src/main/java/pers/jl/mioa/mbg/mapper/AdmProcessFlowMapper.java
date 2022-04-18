package pers.jl.mioa.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.jl.mioa.mbg.entity.AdmProcessFlow;
import pers.jl.mioa.mbg.entity.AdmProcessFlowExample;

public interface AdmProcessFlowMapper {
    long countByExample(AdmProcessFlowExample example);

    int deleteByExample(AdmProcessFlowExample example);

    int deleteByPrimaryKey(Long processId);

    int insert(AdmProcessFlow record);

    int insertSelective(AdmProcessFlow record);

    List<AdmProcessFlow> selectByExample(AdmProcessFlowExample example);

    AdmProcessFlow selectByPrimaryKey(Long processId);

    int updateByExampleSelective(@Param("record") AdmProcessFlow record, @Param("example") AdmProcessFlowExample example);

    int updateByExample(@Param("record") AdmProcessFlow record, @Param("example") AdmProcessFlowExample example);

    int updateByPrimaryKeySelective(AdmProcessFlow record);

    int updateByPrimaryKey(AdmProcessFlow record);
}