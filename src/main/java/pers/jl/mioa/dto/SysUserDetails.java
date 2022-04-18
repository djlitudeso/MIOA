package pers.jl.mioa.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pers.jl.mioa.mbg.entity.SysMenu;
import pers.jl.mioa.mbg.entity.SysUser;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: JL Du
 * @date: 2022/4/9 22:34
 * @version: 1.0.0
 */
public class SysUserDetails implements UserDetails {

    private SysUser sysUser;
    private List<SysMenu> sysMenuList;

    public SysUserDetails(SysUser sysUser,List<SysMenu> sysMenuList){
        this.sysUser = sysUser;
        this.sysMenuList = sysMenuList;
    }

    /**
     * 获取用户的权限集合
     * @return null
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return sysMenuList.stream()
//                .filter(sysMenu -> sysMenu.getValue()!=null)
//                .map(sysMenu ->new SimpleGrantedAuthority(sysMenu.getValue()))
                .filter(sysMenu -> sysMenu.getName()!=null)
                .map(sysMenu ->new SimpleGrantedAuthority(sysMenu.getName()))
                .collect(Collectors.toList());
    }

    /**
     *获取密码
     * @return null
     */
    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    /**
     * 获取用户名
     * @return null
     */
    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    /**
     * 账号是否没过期
     * @return null
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号是否没被锁定
     * @return null
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否没过期
     * @return null
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     *账户是否可用
     * @return null
     */
    @Override
    public boolean isEnabled() {
        //return sysUser.getStatus().equals(1);
        return true;
    }
}
