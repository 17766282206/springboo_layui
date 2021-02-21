package com.cxwmpt.demo.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxwmpt.demo.annotation.SysLog;
import com.cxwmpt.demo.common.result.ResultCodeEnum;
import com.cxwmpt.demo.common.result.ResultMessage;
import com.cxwmpt.demo.model.system.SysDict;
import com.cxwmpt.demo.model.system.SysDictComment;
import com.cxwmpt.demo.model.system.SysUser;
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
 * @create: 2020-05-19 14:45
 **/
@Controller
public class SysDictCommentController {
    private final SysDictService sysDictService;
    private final SysDictCommentService sysDictCommentService;

    public SysDictCommentController(SysDictService sysDictService, SysDictCommentService sysDictCommentService) {
        this.sysDictService = sysDictService;
        this.sysDictCommentService = sysDictCommentService;
    }

    /**
     * 新增页面
     * @return
     */
    @SysLog("打开新增字典详情窗口")
    @RequestMapping("/html/system/dictComment/addPage")
    public String addPage(Model model, String selectDictId) {
        SysDict sysDict = sysDictService.getById(selectDictId);
        SysDictComment sysDictComment=new SysDictComment();
        sysDictComment.setDictCode(sysDict.getCode());
        model.addAttribute("entity", sysDictComment);
        return "systemSetup/userCenter/dict/AddDictComment";
    }

    /**
     * 修改页面
     * @param model
     * @param id
     * @param action
     * @return
     */
    @SysLog("编辑新增字典详情窗口")
    @RequestMapping("/html/system/dictComment/updatePage")
    public String updatePage(Model model, String id, String action) {
        model.addAttribute("action", action);
        if (StringUtils.isNotBlank(id)) {
            SysDictComment sysDictComment = sysDictCommentService.getById(id);
            model.addAttribute("entity", sysDictComment);
        }
        return "systemSetup/userCenter/dict/UpdateDictComment";
    }
    /**
     * 查询所有的数据
     * @param map
     * @return
     */
    @SysLog("分页查询字典详情信息")
    @RequestMapping("/api/auth/dictComment/pageList")
    @ResponseBody
    public ResultMessage pageList(@RequestParam Map<String, Object> map) {

        if (map.containsKey("page") && map.containsKey("limit")) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()),Integer.parseInt(map.get("limit").toString()));
        }

            List<SysDictComment> list = sysDictCommentService.getAllList(map);
            PageInfo<SysDictComment> info = new PageInfo<>(list);
            return ResultMessage.success(info.getList(), (int)info.getTotal());
    }

    /**
     * 新增和修改
     * @param
     * @return
     */
    @SysLog("保存字典详情信息")
    @RequestMapping("/api/auth/dictComment/save")
    @ResponseBody
    public ResultMessage save(SysDictComment sysDictComment) {
        //获取登录人信息
        SysUser loginUser = (SysUser) getSubject().getPrincipal();
        //查询有没有重复的字段
        QueryWrapper<SysDictComment> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code", sysDictComment.getDictCode());
        wrapper.eq("code", sysDictComment.getCode());
        wrapper.eq("del_flag", false);
        int count = sysDictCommentService.count(wrapper);
        //添加新用户验证loginID是否相同
        if (StringUtils.isNotBlank(sysDictComment.getId())) {
            //原数据库数据
            SysDictComment old = sysDictCommentService.getById(sysDictComment.getId());
            if (!sysDictComment.getCode().equals(old.getCode())) {
                if (count != 0) {
                    return ResultMessage.error(-1, "对不起，你修改的编号详情重复，请重新创建");
                }
            }
            //修改
            sysDictComment.setUpdateId(loginUser.getId());
            if (sysDictCommentService.updateById(sysDictComment)) {
                return ResultMessage.success();
            }
            return ResultMessage.error(ResultCodeEnum.UPDATE_DATE_ERROR);
        } else {
            //新增
            if (count != 0) {
                return ResultMessage.error(-1, "对不起，你创建的编号重复，请重新创建");
            }
            sysDictComment.setCreateId(loginUser.getId());
            if(sysDictCommentService.save(sysDictComment)){
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
    @SysLog("删除字典详情信息")
    @RequestMapping("/api/auth/dictComment/deletes")
    @ResponseBody
    public ResultMessage deletes(@RequestParam("ids[]") List<String> ids) {
        if (ids.size() <= 0) {
            return ResultMessage.error(ResultCodeEnum.OPERATION_DATA_IS_NULL);
        }
        for (String data : ids) {
            sysDictCommentService.removeById(data);

        }
        return ResultMessage.success();
    }

}
