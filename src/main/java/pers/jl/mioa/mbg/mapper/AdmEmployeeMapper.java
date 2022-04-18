package pers.jl.mioa.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.jl.mioa.mbg.entity.AdmEmployee;
import pers.jl.mioa.mbg.entity.AdmEmployeeExample;

public interface AdmEmployeeMapper {
    long countByExample(AdmEmployeeExample example);

    int deleteByExample(AdmEmployeeExample example);

    int deleteByPrimaryKey(Long employeeId);

    int insert(AdmEmployee record);

    int insertSelective(AdmEmployee record);

    List<AdmEmployee> selectByExample(AdmEmployeeExample example);

    AdmEmployee selectByPrimaryKey(Long employeeId);

    int updateByExampleSelective(@Param("record") AdmEmployee record, @Param("example") AdmEmployeeExample example);

    int updateByExample(@Param("record") AdmEmployee record, @Param("example") AdmEmployeeExample example);

    int updateByPrimaryKeySelective(AdmEmployee record);

    int updateByPrimaryKey(AdmEmployee record);
}