package com.cxwmpt.demo.dao.system;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxwmpt.demo.model.system.SysDict;
import com.cxwmpt.demo.model.system.SysJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Repository
public interface SysJobMapper extends BaseMapper<SysJob> {
    /**
     * 获取所有数据
     * @param map
     * @return
     */
    List<SysJob> getAllList(@Param("map") Map map);
}