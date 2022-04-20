package pers.jl.mioa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.jl.mioa.mapper.NoticeCusMapper;
import pers.jl.mioa.mbg.entity.SysNotice;
import pers.jl.mioa.service.SysNoticeService;

import java.util.List;

/**
 * @author: JL Du
 * @date: 2022/4/20 23:18
 * @version: 1.0.0
 */
@Service
public class SysNoticeServiceImpl implements SysNoticeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysNoticeServiceImpl.class);

    @Autowired
    private NoticeCusMapper noticeCusMapper;

    /**
     * 获取系统消息列表
     *
     * @param receiveId 接收者id
     * @return 消息列表
     */
    @Override
    public List<SysNotice> getNoticeList(Long receiveId) {

        return noticeCusMapper.selectNoticeByReceiveId(receiveId);

    }

}
