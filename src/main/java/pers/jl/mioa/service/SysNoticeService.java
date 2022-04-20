package pers.jl.mioa.service;

import pers.jl.mioa.mbg.entity.SysNotice;

import java.util.List;

/**
 * @author: JL Du
 * @date: 2022/4/20 23:18
 * @version: 1.0.0
 */
public interface SysNoticeService {

    /**
     * 获取消息列表
     *
     * @param receiveId  接收者id
     * @return 消息
     */
    List<SysNotice> getNoticeList(Long receiveId);
}
