package com.cxwmpt.demo.service.impl.system;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxwmpt.demo.dao.system.SysRoleMapper;
import com.cxwmpt.demo.model.system.SysRole;
import com.cxwmpt.demo.service.api.system.SysRoleService;
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
* @create: 2020-04-08 17:01
**/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    protected SysRoleMapper sysRoleMapper;
    @Override
    public List<SysRole> getAllList(Map map) {
        return sysRoleMapper.getAllList(map);
    }
}
