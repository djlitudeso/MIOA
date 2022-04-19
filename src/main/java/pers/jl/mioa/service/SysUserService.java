package pers.jl.mioa.service;

import org.springframework.security.core.userdetails.UserDetails;
import pers.jl.mioa.dto.SysUserRegisterParam;
import pers.jl.mioa.dto.UpdateUserPasswordParam;
import pers.jl.mioa.mbg.entity.SysMenu;
import pers.jl.mioa.mbg.entity.SysUser;

import java.util.List;

/**
 * @author: JL Du
 * @date: 2022/4/9 23:47
 * @version: 1.0.0
 */
public interface SysUserService {

    /**
     *根据用户名获取用户
     *
     * @param username 用户名
     * @return null
     */
    SysUser getUserByUsername(String username);

    /**
     *注册功能
     *
     * @param sysUserParam null
     * @return null
     */
    SysUser register(SysUserRegisterParam sysUserParam);

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     * @param userId 用户id
     * @return null
     */
    List<SysMenu> getMenuList(Long userId);

    /**
     *根据用户id获取用户
     *
     * @param userId null
     * @return null
     */
    SysUser getUserById(Long userId);

    /**
     * 修改密码
     * @param updatePasswordParam null
     * @return null
     */
    int updatePassword(UpdateUserPasswordParam updatePasswordParam);

    /**
     * 获取用户信息
     * @param username null
     * @return null
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 从token中获取用户信息
     *
     * @return 用户
     */
    SysUser getUserByToken();

}
