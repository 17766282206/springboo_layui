package com.cxwmpt.demo.service.impl.system;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxwmpt.demo.dao.system.SysJobMapper;
import com.cxwmpt.demo.model.system.SysJob;
import com.cxwmpt.demo.service.api.system.SysJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {

    protected final SysJobMapper sysJobMapper;

    public SysJobServiceImpl(SysJobMapper sysJobMapper) {
        this.sysJobMapper = sysJobMapper;
    }

    @Override
    public List<SysJob> getAllList(Map map) {
        return sysJobMapper.getAllList(map);
    }
}
