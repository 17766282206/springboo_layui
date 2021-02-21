package com.cxwmpt.demo.service.api.system;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cxwmpt.demo.model.system.SysDict;
import com.cxwmpt.demo.model.system.SysDictComment;
import com.cxwmpt.demo.model.system.SysLog;

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
public interface SysDictCommentService extends IService<SysDictComment> {


    /**
     * 获取所有数据
     * @param map
     * @return
     */
    List<SysDictComment> getAllList(Map<String, Object> map);
}
