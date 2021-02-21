package com.cxwmpt.demo.service.api.system;



import com.baomidou.mybatisplus.extension.service.IService;
import com.cxwmpt.demo.model.system.SysRole;

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
public interface SysRoleService extends IService<SysRole> {


    /**
     * 获取所有数据
     * @param map
     * @return
     */
    List<SysRole> getAllList(Map map);
}
