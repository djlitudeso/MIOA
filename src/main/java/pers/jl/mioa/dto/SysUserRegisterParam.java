package pers.jl.mioa.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author: JL Du
 * @date: 2022/4/11 22:32
 * @version: 1.0.0
 */
@Getter
@Setter
public class SysUserRegisterParam {
    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @Email
    @ApiModelProperty(value = "邮箱")
    private String email;

}
