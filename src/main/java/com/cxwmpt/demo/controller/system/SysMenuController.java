package com.cxwmpt.demo.controller.system;



import com.cxwmpt.demo.annotation.SysLog;
import com.cxwmpt.demo.common.result.ResultCodeEnum;
import com.cxwmpt.demo.common.result.ResultMessage;
import com.cxwmpt.demo.common.vo.Node;
import com.cxwmpt.demo.model.system.SysMenu;
import com.cxwmpt.demo.model.system.SysUser;
import com.cxwmpt.demo.service.api.system.SysMenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static org.apache.shiro.SecurityUtils.getSubject;


/**
 * @author Administrator
 */
@Controller
public class SysMenuController {
    private final SysMenuService sysMenuService;

    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    /**
     * 菜单栏页面
     *
     * @return
     */
    @SysLog("打开菜单管理窗口")
    @RequestMapping("html/system/menu/page")
    public String page() {
        return "systemSetup/userCenter/menu/Page";
    }

    /**
     * 图标页
     * @return
     */
    @SysLog("打开图标窗口")
    @GetMapping("html/menu/icon")
    public String icon() {
        return "systemSetup/userCenter/menu/icon";
    }

    @SysLog("打开新增菜单管理窗口")
    @RequestMapping("/html/system/menu/addPage")
    public String addPage(Model model,String parentId) {
        SysMenu sysMenu =new SysMenu();
        sysMenu.setParentId(parentId);
        model.addAttribute("entity",sysMenu);
        return "systemSetup/userCenter/menu/Add";
    }

    @SysLog("打开编辑菜单管理窗口")
    @RequestMapping("/html/system/menu/updatePage")
    public String updatePage(Model model, String id, String action) {
        model.addAttribute("action", action);
        if (StringUtils.isNotBlank(id)) {
            SysMenu sysMenu = sysMenuService.getById(id);
            model.addAttribute("entity", sysMenu);
        }
        return "systemSetup/userCenter/menu/Update";
    }


    /**
     * 获取登录人要显示的菜单(只查询角色状态为0的角色)
     */
    @SysLog("获取登录人要显示的菜单(只查询角色状态为0的角色)")
    @RequestMapping("/api/auth/menu/getListByLoginInfo")
    @ResponseBody
    public ResultMessage getListByLoginInfo() {
        //获取登录人信息
        SysUser loginUser = (SysUser) getSubject().getPrincipal();
        List<Node> sysMenuList=sysMenuService.getListByLoginInfo(loginUser.getId());
        return  ResultMessage.success(sysMenuList);
    }

    /**
     * dTreeList的数据
     */
    @SysLog("查询菜单信息")
    @RequestMapping("/api/auth/menu/getDTreeList")
    @ResponseBody
    public  ResultMessage getDTreeList() {
        List<Map> list=sysMenuService.getDTreeList();
        return  ResultMessage.success("查询所有菜单信息",list);
    }


    /**
     * 根据pid下面的所有子节点包含自己(菜单界面使用)
     * orderby
     */
    @SysLog("根据pid下面的所有子节点包含自己(菜单界面使用)")
    @PostMapping("/api/auth/menu/getListById")
    @ResponseBody
    public ResultMessage getListByPid(@RequestParam Map map) {
        if (map.containsKey("page") && map.containsKey("limit")) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("limit").toString()));
        }
        List<SysMenu> list = sysMenuService.getListById(map);
        PageInfo<SysUser> info = new PageInfo(list);
        return ResultMessage.success(info);
    }
    /***
     * 删除自己和子节点
     * @param id
     * @return
     */
    @SysLog("删除菜单信息（包含子节点信息）")
    @RequestMapping("/api/auth/menu/delete")
    @ResponseBody
    public ResultMessage delete(String id) {
        List<String> sysMentId=sysMenuService.getByIDSelectSubNode(id);
        if(!sysMenuService.removeById(id)){
            return ResultMessage.error(ResultCodeEnum.DELETE_DATE_ERROR);
        }
        for (String data :sysMentId) {
           sysMenuService.removeById(data);
        }
        return ResultMessage.success();
    }
    /***
     * 删除自己和子节点
     * @param ids
     * @return
     */
    @SysLog("批量删除菜单信息（包含子节点信息）")
    @RequestMapping("/api/auth/menu/deletes")
    @ResponseBody
    public ResultMessage delete(@RequestParam("ids[]") List<String> ids) {
        if (ids.size() <= 0) {
            return ResultMessage.error(ResultCodeEnum.OPERATION_DATA_IS_NULL);
        }
        for (String data : ids) {
            List<String> sysMentId = sysMenuService.getByIDSelectSubNode(data);
            sysMenuService.removeById(data);
            for (String data1 : sysMentId) {
                sysMenuService.removeById(data1);
            }
        }
        return ResultMessage.success();
    }
    /**
     * 新增和修改
     * @param sysMenu
     * @return
     */
    @SysLog("保存菜单信息")
    @RequestMapping("/api/auth/menu/save")
    @ResponseBody
    public ResultMessage save(SysMenu sysMenu) {
        //获取登录人信息
        SysUser loginUser = (SysUser) getSubject().getPrincipal();
        if (("").equals(sysMenu.getParentId()) || sysMenu.getParentId() == null) {
            sysMenu.setParentId(0+"");
        }
        //添加新用户验证loginID是否相同
        if (sysMenu.getId() != null&&sysMenu.getId().length()>0) {
            //修改
           if(sysMenu.getId().equals(sysMenu.getParentId())){
               return ResultMessage.error(-1,"请不要选中自身作为父节点");
           }
            sysMenu.setUpdateId(loginUser.getId());
            if (sysMenuService.updateById(sysMenu)) {
                return ResultMessage.success();
            }
            return ResultMessage.error(ResultCodeEnum.UPDATE_DATE_ERROR);
        } else {
            //新增
            sysMenu.setCreateId(loginUser.getId());
            if(sysMenuService.save(sysMenu)){
                System.out.println(sysMenu.getId());
                return ResultMessage.success();
            }
            return  ResultMessage.error(ResultCodeEnum.ADD_DATE_ERROR);
        }
    }



    /**
     * 根据pid查询菜单栏
     * @param parentId
     * @return
     */
    @SysLog("根据菜单父节点查询菜单信息")
    @RequestMapping("/api/auth/menu/listFromPid")
    @ResponseBody
    public  ResultMessage listFromPid(@RequestParam("parentId") String parentId) {
        SysMenu sysMenu=sysMenuService.getById(parentId);
         if(sysMenu==null){
             return ResultMessage.error(-1,"根据pid获取数据为空");
         }
        return ResultMessage.success(sysMenu);
    }
}
