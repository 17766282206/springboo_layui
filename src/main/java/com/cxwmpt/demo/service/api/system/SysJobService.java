package com.cxwmpt.demo.service.api.system;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cxwmpt.demo.model.system.SysDict;
import com.cxwmpt.demo.model.system.SysJob;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface SysJobService extends IService<SysJob> {


    /**
     * 获取所有数据
     * @param map
     * @return
     */
    List<SysJob> getAllList(Map map);

}
