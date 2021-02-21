package com.cxwmpt.demo.dao.system;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxwmpt.demo.model.system.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询所有数据
     * @param map
     * @return
     */
    List<SysRole> getAllList(@Param("map") Map map);
}