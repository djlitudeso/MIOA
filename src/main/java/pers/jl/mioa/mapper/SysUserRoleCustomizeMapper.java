package pers.jl.mioa.mapper;

import org.apache.ibatis.annotations.Param;
import pers.jl.mioa.mbg.entity.SysMenu;

import java.util.List;

/**
 * @author: JL Du
 * @date: 2022/4/10 20:35
 * @version: 1.0.0
 */
public interface SysUserRoleCustomizeMapper {

    /**
     * 获取用户所有权限(包括+-权限)
     * @param userId null
     * @return null
     */
    List<SysMenu> getMenuList(@Param("userId") Long userId);
}
