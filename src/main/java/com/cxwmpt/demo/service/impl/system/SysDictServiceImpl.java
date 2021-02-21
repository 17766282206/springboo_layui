package com.cxwmpt.demo.service.impl.system;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxwmpt.demo.dao.system.SysDictMapper;
import com.cxwmpt.demo.model.system.SysDict;
import com.cxwmpt.demo.service.api.system.SysDictService;
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
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    protected final SysDictMapper sysDictMapper;

    public SysDictServiceImpl(SysDictMapper sysDictMapper) {
        this.sysDictMapper = sysDictMapper;
    }

    @Override
    public List<SysDict> getAllList(Map map) {
        return sysDictMapper.getAllList(map);
    }
}
