package com.cxwmpt.demo.service.impl.system;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxwmpt.demo.dao.system.SysDictCommentMapper;
import com.cxwmpt.demo.model.system.SysDictComment;
import com.cxwmpt.demo.service.api.system.SysDictCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @program: backend
*
* @description
*
* @author: YouName
*
* @create: 2020-04-03 10:15
**/
@Service
public class SysDictCommentServiceImpl extends ServiceImpl<SysDictCommentMapper, SysDictComment> implements SysDictCommentService {

    protected final SysDictCommentMapper sysDictCommentMapper;

    public SysDictCommentServiceImpl(SysDictCommentMapper sysDictCommentMapper) {
        this.sysDictCommentMapper = sysDictCommentMapper;
    }

    @Override
    public List<SysDictComment> getAllList(Map map) {
        return sysDictCommentMapper.getAllList(map);
    }
}
