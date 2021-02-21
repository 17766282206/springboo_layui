package com.cxwmpt.demo.dao.system;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxwmpt.demo.model.system.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* Created by Mybatis Generator 2020/04/07
 * @author Administrator
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {


    /**
     * 根据登录人获取当前所有用户信息和权限信息
     * @param loginUser
     * @return
     */
    SysUser getPermissionsInfoByUserInfo(@Param("loginUser") SysUser loginUser);


    /**
     * 获取所有数据
     * @param map
     * @return
     */
    List<SysUser> getAllList(@Param("map") Map map);
}