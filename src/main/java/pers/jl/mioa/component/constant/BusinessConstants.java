package pers.jl.mioa.component.constant;

/**
 * 业务常量定义
 * @author: JL Du
 * @date: 2022/4/18 15:24
 * @version: 1.0.0
 */
public class BusinessConstants {

    /** 总经理级别 */
    public static final int GENERAL_MANAGER_LEVEL = 8;

    /** 部门经理级别 */
    public static final int DEPARTMENT_MANAGER_LEVEL = 7;

    /** 总经理请假审批时间阈值 */
    public static final int MANAGER_AUDIT_HOURS = 72;

    /** 审批结果：同意  */
    public static final String AUDIT_RESULT_APPROVED = "approved";

    /** 审批结果：驳回  */
    public static final String AUDIT_RESULT_REFUSED = "refused";

}
