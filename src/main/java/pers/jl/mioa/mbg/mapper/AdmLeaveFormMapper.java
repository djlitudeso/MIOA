package pers.jl.mioa.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.jl.mioa.mbg.entity.AdmLeaveForm;
import pers.jl.mioa.mbg.entity.AdmLeaveFormExample;

public interface AdmLeaveFormMapper {
    long countByExample(AdmLeaveFormExample example);

    int deleteByExample(AdmLeaveFormExample example);

    int deleteByPrimaryKey(Long formId);

    int insert(AdmLeaveForm record);

    int insertSelective(AdmLeaveForm record);

    List<AdmLeaveForm> selectByExample(AdmLeaveFormExample example);

    AdmLeaveForm selectByPrimaryKey(Long formId);

    int updateByExampleSelective(@Param("record") AdmLeaveForm record, @Param("example") AdmLeaveFormExample example);

    int updateByExample(@Param("record") AdmLeaveForm record, @Param("example") AdmLeaveFormExample example);

    int updateByPrimaryKeySelective(AdmLeaveForm record);

    int updateByPrimaryKey(AdmLeaveForm record);
}