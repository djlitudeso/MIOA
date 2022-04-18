package pers.jl.mioa.mapper;

import pers.jl.mioa.mbg.entity.SysNotice;

/**
 * @author: JL Du
 * @date: 2022/4/17 18:53
 * @version: 1.0.0
 */
public interface NoticeCusMapper {

    /**
     * 创建
     * @param notice null
     */
    void createNotice(SysNotice notice);
}
