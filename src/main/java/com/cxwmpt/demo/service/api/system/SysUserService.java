package com.cxwmpt.demo.service.api.system;



import com.baomidou.mybatisplus.extension.service.IService;
import com.cxwmpt.demo.model.system.SysUser;

import java.util.List;
import java.util.Map;

/**
 *
 * @author admin
 * @date 2020/4/7
 */
public interface SysUserService extends IService<SysUser> {


    /**
     * 查找所有数据
     * @param map
     * @return
     */
    List<SysUser> getAllList(Map map);


    /**
     * 根据用户信息查找用户信息和权限信息
     * @param loginUser
     * @return
     */
    SysUser getPermissionsInfoByUserInfo(SysUser loginUser);
}
