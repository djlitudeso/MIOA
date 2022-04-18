package pers.jl.mioa.mbg.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class AdmProcessFlow implements Serializable {
    private Long processId;

    @ApiModelProperty(value = "请假单编号")
    private Long formId;

    @ApiModelProperty(value = "经办人编号")
    private Long operatorId;

    @ApiModelProperty(value = "操作类型：apply-申请，audit-审批")
    private String action;

    @ApiModelProperty(value = "审批结果：approved-同意，refused-驳回")
    private String result;

    @ApiModelProperty(value = "审批意见")
    private String reason;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "审批时间")
    private Date auditTime;

    @ApiModelProperty(value = "任务序号")
    private Integer orderNo;

    @ApiModelProperty(value = "流程状态：ready-准备，process-正在处理，complete-处理完成，cancel-取消")
    private String state;

    @ApiModelProperty(value = "是否最后节点：0-否，1-是")
    private Integer isLast;

    private static final long serialVersionUID = 1L;

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getIsLast() {
        return isLast;
    }

    public void setIsLast(Integer isLast) {
        this.isLast = isLast;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", processId=").append(processId);
        sb.append(", formId=").append(formId);
        sb.append(", operatorId=").append(operatorId);
        sb.append(", action=").append(action);
        sb.append(", result=").append(result);
        sb.append(", reason=").append(reason);
        sb.append(", createTime=").append(createTime);
        sb.append(", auditTime=").append(auditTime);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", state=").append(state);
        sb.append(", isLast=").append(isLast);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}