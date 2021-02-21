package com.cxwmpt.demo.service.impl.system;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxwmpt.demo.common.util.TreeBuilder;
import com.cxwmpt.demo.common.vo.Node;
import com.cxwmpt.demo.dao.system.SysMenuMapper;
import com.cxwmpt.demo.model.system.SysMenu;
import com.cxwmpt.demo.service.api.system.SysMenuService;

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
* @create: 2020-04-08 17:02
**/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    protected final SysMenuMapper sysMenuMapper;

    public SysMenuServiceImpl(SysMenuMapper sysMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
    }


    @Override
    public List<Map> getDTreeList() {
        return sysMenuMapper.getDTreeList();
    }

    @Override
    public List<String> getByIDSelectSubNode(String id) {
        return sysMenuMapper.getByIDSelectSubNode(id);
    }

    @Override
    public List<Node> getListByLoginInfo(String id) {
        List<Node> listTreeTable =sysMenuMapper.getListByLoginInfo(id);
        //转换成子父节点
        TreeBuilder treeBuilder = new TreeBuilder(listTreeTable);
        return treeBuilder.buildTree();
    }



    @Override
    public List<SysMenu> getListById(Map map) {
        return sysMenuMapper.getListById(map);
    }
}
