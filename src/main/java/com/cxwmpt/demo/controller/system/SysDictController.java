package com.cxwmpt.demo.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxwmpt.demo.annotation.SysLog;
import com.cxwmpt.demo.common.result.ResultCodeEnum;
import com.cxwmpt.demo.common.result.ResultMessage;
import com.cxwmpt.demo.model.system.*;
import com.cxwmpt.demo.service.api.system.SysDictCommentService;
import com.cxwmpt.demo.service.api.system.SysDictService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.apache.shiro.SecurityUtils.getSubject;

/**
 * @program: backend
 * @description
 * @author: YouName
 * @create: 2020-05-20 16:53
 **/
@Controller
public class SysDictController {

    private final SysDictService sysDictService;

    private final SysDictCommentService sysDictCommentService;

    public SysDictController(SysDictService sysDictService, SysDictCommentService sysDictCommentService) {
        this.sysDictService = sysDictService;
        this.sysDictCommentService = sysDictCommentService;
    }

    /**
     * 字典列表页面
     * @return
     */
    @SysLog("打开字典管理窗口")
    @RequestMapping("/html/system/dict/page")
    public String page() {
        return "systemSetup/userCenter/dict/Page";
    }

    /**
     * 新增页面
     * @return
     */
    @SysLog("打开新增字典管理窗口")
    @RequestMapping("/html/system/dict/addPage")
    public String addPage() {
        return "systemSetup/userCenter/dict/Add";
    }

    /**
     * 修改页面
     * @param model
     * @param id
     * @param action
     * @return
     */
    @SysLog("打开编辑字典管理窗口")
    @RequestMapping("/html/system/dict/updatePage")
    public String updatePage(Model model, String id, String action) {
        model.addAttribute("action", action);
        if (StringUtils.isNotBlank(id)) {
            SysDict sysDict = sysDictService.getById(id);
            model.addAttribute("entity", sysDict);
        }
        return "systemSetup/userCenter/dict/Update";
    }


    /**
     * 查询所有的数据
     * @param map
     * @return
     */
    @SysLog("分页查询字典管理信息")
    @RequestMapping("/api/auth/dict/pageList")
    @ResponseBody
    public ResultMessage pageList(@RequestParam Map<String, Object> map) {

        if (map.containsKey("page") && map.containsKey("limit")) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()),Integer.parseInt(map.get("limit").toString()));
        }

        List<SysDict> list = sysDictService.getAllList(map);

        PageInfo<SysDict> info = new PageInfo<>(list);

        return ResultMessage.success(info.getList(), (int)info.getTotal());
    }

    /**
     * 新增和修改
     * @param sysDict
     * @return
     */
    @SysLog("保存字典管理信息")
    @RequestMapping("/api/auth/dict/save")
    @ResponseBody
    public ResultMessage save(SysDict sysDict) {
        //获取登录人信息
        SysUser loginUser = (SysUser) getSubject().getPrincipal();
        //查询有没有重复的字段
        QueryWrapper<SysDict> wrapper = new QueryWrapper<>();
        wrapper.eq("code", sysDict.getCode());
        wrapper.eq("del_flag", false);
        int count = sysDictService.count(wrapper);
        //添加新用户验证loginID是否相同
        if (StringUtils.isNotBlank(sysDict.getId())) {
            //原数据库数据
            SysDict old = sysDictService.getById(sysDict.getId());
            if (!sysDict.getCode().equals(old.getCode())) {
                if (count != 0) {
                    return ResultMessage.error(-1, "对不起，你修改的编号重复，请重新创建");
                }
            }
            //修改
            sysDict.setUpdateId(loginUser.getId());
            if (sysDictService.updateById(sysDict)) {
                return ResultMessage.success();
            }
            return ResultMessage.error(ResultCodeEnum.UPDATE_DATE_ERROR);
        } else {
            //新增
            if (count != 0) {
                return ResultMessage.error(-1, "对不起，你创建的编号重复，请重新创建");
            }
            sysDict.setCreateId(loginUser.getId());
            if(sysDictService.save(sysDict)){
                return ResultMessage.success();
            }
            return  ResultMessage.error(ResultCodeEnum.ADD_DATE_ERROR);
        }
    }
    /**
     * 删除数据字典
     * @param ids
     * @return
     */
    @SysLog("删除数据字典")
    @RequestMapping("/api/auth/dict/deletes")
    @ResponseBody
    public ResultMessage deletes(@RequestParam("ids[]") List<String> ids) {
        if (ids.size() <= 0) {
            return ResultMessage.error(ResultCodeEnum.OPERATION_DATA_IS_NULL);
        }
        for (String data : ids) {
            SysDict dict = sysDictService.getById(data);
            sysDictService.removeById(data);
            QueryWrapper<SysDictComment> wrapper = new QueryWrapper<>();
            wrapper.eq("dict_code", dict.getCode());
            wrapper.eq("del_flag", false);
            sysDictCommentService.remove(wrapper);
        }
        return ResultMessage.success();
    }

}
