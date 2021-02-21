package com.cxwmpt.demo.dao.system;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxwmpt.demo.common.vo.Node;
import com.cxwmpt.demo.model.system.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {


    /**
     * layui 下拉列表树的数据源
     * @return
     */
    List<Map>  getDTreeList();


    /**
     * 根据id查询子节点所有id
     * @param id
     * @return
     */
    List<String> getByIDSelectSubNode(@Param("id") String id);


    /**
     * 获取登录人拥有权限的菜单信息
     * @param id
     * @return
     */
    List<Node> getListByLoginInfo(@Param("id") String id);


    /**
     * 根据id查询出子节点和它本身
     * @param map
     * @return
     */
    List<SysMenu> getListById(@Param("map")Map map);
}