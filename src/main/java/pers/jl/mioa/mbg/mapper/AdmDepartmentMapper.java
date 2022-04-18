package pers.jl.mioa.mbg.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.jl.mioa.mbg.entity.AdmDepartment;
import pers.jl.mioa.mbg.entity.AdmDepartmentExample;

public interface AdmDepartmentMapper {
    long countByExample(AdmDepartmentExample example);

    int deleteByExample(AdmDepartmentExample example);

    int deleteByPrimaryKey(Long deptId);

    int insert(AdmDepartment record);

    int insertSelective(AdmDepartment record);

    List<AdmDepartment> selectByExample(AdmDepartmentExample example);

    AdmDepartment selectByPrimaryKey(Long deptId);

    int updateByExampleSelective(@Param("record") AdmDepartment record, @Param("example") AdmDepartmentExample example);

    int updateByExample(@Param("record") AdmDepartment record, @Param("example") AdmDepartmentExample example);

    int updateByPrimaryKeySelective(AdmDepartment record);

    int updateByPrimaryKey(AdmDepartment record);
}