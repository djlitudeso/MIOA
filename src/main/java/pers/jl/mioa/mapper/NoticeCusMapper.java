package pers.jl.mioa.mapper;

import org.apache.ibatis.annotations.Param;
import pers.jl.mioa.mbg.entity.SysNotice;

import java.util.List;

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

    /**
     * null
     * @param receiveId  null
     * @return null
     */
    List<SysNotice> selectNoticeByReceiveId(@Param("receiveId") Long receiveId);

}
