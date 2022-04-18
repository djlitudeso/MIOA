package pers.jl.mioa.mbg.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AdmEmployee implements Serializable {
    private Long employeeId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "部门编号")
    private Long departmentId;

    @ApiModelProperty(value = "职称")
    private String title;

    @ApiModelProperty(value = "行政级别")
    private Integer level;

    private static final long serialVersionUID = 1L;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", employeeId=").append(employeeId);
        sb.append(", name=").append(name);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", title=").append(title);
        sb.append(", level=").append(level);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}