package com.cxwmpt.demo.dao.system;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxwmpt.demo.model.system.SysDictComment;
import com.cxwmpt.demo.model.system.SysLog;
import org.apache.ibatis.annotations.Mapper;
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
* @create: 2020-04-03 10:15
**/
@Repository
public interface SysDictCommentMapper extends BaseMapper<SysDictComment> {

    /**
     * 获取所有数据
     * @param map
     * @return
     */
    List<SysDictComment> getAllList(@Param("map") Map map);
}