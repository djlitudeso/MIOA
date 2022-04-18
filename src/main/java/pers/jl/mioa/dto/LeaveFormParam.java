package pers.jl.mioa.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author: JL Du
 * @date: 2022/4/18 18:33
 * @version: 1.0.0
 */

public class LeaveFormParam {

    @ApiModelProperty(value = "员工id",allowEmptyValue = true)
    private Long employeeId;

    @ApiModelProperty(value = "请假类型：1-事假，2-病假，3-工伤假，4-婚假，5-产假，6-丧假")
    private Integer formType;

    @ApiModelProperty(value = "起始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "请假事由")
    private String reason;

    @ApiModelProperty(value = "创建时间",hidden = true,allowEmptyValue = true)
    private Date createTime;

    @ApiModelProperty(value = "请假单状态：processing-正在审批，approved-审批已通过，refused-审批被驳回",
            hidden = true,allowEmptyValue = true)
    private String state;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
