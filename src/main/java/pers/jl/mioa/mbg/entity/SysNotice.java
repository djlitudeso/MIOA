package pers.jl.mioa.mbg.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * @author DJ
 */
public class SysNotice implements Serializable {
    private Long noticeId;

    @ApiModelProperty(value = "接收者编号")
    private Long receiverId;

    @ApiModelProperty(value = "消息正文")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public SysNotice(){

    }

    public SysNotice(Long receiverId, String content) {
        this.receiverId = receiverId;
        this.content = content;
        this.createTime = new Date();
    }

    private static final long serialVersionUID = 1L;

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", noticeId=").append(noticeId);
        sb.append(", receiverId=").append(receiverId);
        sb.append(", content=").append(content);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}