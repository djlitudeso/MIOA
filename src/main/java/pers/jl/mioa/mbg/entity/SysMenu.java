package pers.jl.mioa.mbg.entity;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class SysMenu implements Serializable {
    private Long menuId;

    @ApiModelProperty(value = "父级权限id")
    private Long pid;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "权限值")
    private String component;

    @ApiModelProperty(value = "节点编码，用于排序")
    private Integer code;

    @ApiModelProperty(value = "权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    private Integer type;

    @ApiModelProperty(value = "前端资源路径")
    private String url;

    @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用")
    private Integer status;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "计数")
    private Integer subCount;

    @ApiModelProperty(value = "排序")
    private Integer menuSort;

    private static final long serialVersionUID = 1L;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSubCount() {
        return subCount;
    }

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }

    public Integer getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", menuId=").append(menuId);
        sb.append(", pid=").append(pid);
        sb.append(", name=").append(name);
        sb.append(", component=").append(component);
        sb.append(", code=").append(code);
        sb.append(", type=").append(type);
        sb.append(", url=").append(url);
        sb.append(", status=").append(status);
        sb.append(", icon=").append(icon);
        sb.append(", subCount=").append(subCount);
        sb.append(", menuSort=").append(menuSort);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}