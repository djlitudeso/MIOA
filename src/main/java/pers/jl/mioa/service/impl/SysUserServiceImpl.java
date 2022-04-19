package pers.jl.mioa.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pers.jl.mioa.common.exception.Asserts;
import pers.jl.mioa.dto.SysUserDetails;
import pers.jl.mioa.dto.SysUserRegisterParam;
import pers.jl.mioa.dto.UpdateUserPasswordParam;
import pers.jl.mioa.mapper.SysUserRoleCustomizeMapper;
import pers.jl.mioa.mbg.entity.SysMenu;
import pers.jl.mioa.mbg.entity.SysUser;
import pers.jl.mioa.mbg.entity.SysUserExample;
import pers.jl.mioa.mbg.mapper.SysUserMapper;
import pers.jl.mioa.security.util.JwtTokenUtil;
import pers.jl.mioa.service.SysUserService;

import java.util.Date;
import java.util.List;

/**
 * @author: JL Du
 * @date: 2022/4/9 23:52
 * @version: 1.0.0
 */

@Service
public class SysUserServiceImpl implements SysUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserServiceImpl.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleCustomizeMapper sysUserRoleCustomizeMapper;

    private String loginToken;

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return null
     */
    @Override
    public SysUser getUserByUsername(String username) {
        SysUserExample example = new SysUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<SysUser> userList = sysUserMapper.selectByExample(example);
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }

    /**
     * 注册功能
     *
     * @param sysUserParam null
     * @return null
     */
    @Override
    public SysUser register(SysUserRegisterParam sysUserParam) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserParam, sysUser);
        sysUser.setCreatTime(new Date());
        sysUser.setStatus(1);
        //查询是否有相同用户名的用户
        SysUserExample example = new SysUserExample();
        example.createCriteria().andUsernameEqualTo(sysUser.getUsername());
        List<SysUser> userList = sysUserMapper.selectByExample(example);
        if (userList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(sysUser.getPassword());
        sysUser.setPassword(encodePassword);
        sysUserMapper.insert(sysUser);
        return sysUser;
    }

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                //throw new BadCredentialsException("密码不正确");
                Asserts.fail("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        loginToken = token;
        return token;
    }

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     *
     * @param userId 用户id
     * @return null
     */
    @Override
    public List<SysMenu> getMenuList(Long userId) {
        return sysUserRoleCustomizeMapper.getMenuList(userId);
    }

    /**
     *根据用户id获取用户
     *
     * @param userId null
     * @return null
     */
    @Override
    public SysUser getUserById(Long userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    /**
     * 修改密码
     *
     * @param param null
     * @return null
     */
    @Override
    public int updatePassword(UpdateUserPasswordParam param) {
        if(StrUtil.isEmpty(param.getUsername())
                ||StrUtil.isEmpty(param.getOldPassword())
                ||StrUtil.isEmpty(param.getNewPassword())){
            return -1;
        }
        SysUserExample example = new SysUserExample();
        example.createCriteria().andUsernameEqualTo(param.getUsername());
        List<SysUser> userList = sysUserMapper.selectByExample(example);
        if(CollUtil.isEmpty(userList)){
            return -2;
        }
        SysUser sysUser = userList.get(0);
        if(!passwordEncoder.matches(param.getOldPassword(),sysUser.getPassword())){
            return -3;
        }
        sysUser.setPassword(passwordEncoder.encode(param.getNewPassword()));
        sysUserMapper.updateByPrimaryKey(sysUser);

        return 1;
    }

    /**
     * 获取用户信息
     *
     * @param username null
     * @return null
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取登录用户信息
        SysUser sysUser = getUserByUsername(username);
        if (sysUser != null) {
            List<SysMenu> sysMenuList = getMenuList(sysUser.getUserId());
            return new SysUserDetails(sysUser,sysMenuList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    /**
     * 从token中获取用户信息
     *
     * @return 用户
     */
    @Override
    public SysUser getUserByToken() {
        String userName = jwtTokenUtil.getUserNameFromToken(loginToken);
        SysUser sysUser;
        sysUser = getUserByUsername(userName);
        return sysUser;
    }

}
