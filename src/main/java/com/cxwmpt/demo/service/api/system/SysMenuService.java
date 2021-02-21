package com.cxwmpt.demo.service.api.system;



import com.baomidou.mybatisplus.extension.service.IService;
import com.cxwmpt.demo.common.vo.Node;
import com.cxwmpt.demo.model.system.SysMenu;

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
public interface SysMenuService extends IService<SysMenu> {


    /**
     * layui 下拉树形数据
     * @return
     */
    List<Map> getDTreeList();

    /**
     * 根据id查询子节点所有id
     * @param id
     * @return
     */
    List<String> getByIDSelectSubNode(String id);

    /**
     * 获取登录人拥有的菜单信息
     * @param id
     * @return
     */
    List<Node> getListByLoginInfo(String id);


    /**
     * 根据id查询出子节点和它本身
     * @param map
     * @return
     */
    List<SysMenu> getListById(Map map);
}
